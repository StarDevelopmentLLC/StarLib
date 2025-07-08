package com.stardevllc.eventbus.impl;

import com.stardevllc.eventbus.*;
import com.stardevllc.helper.ReflectionHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class SimpleEventBus<T> implements IEventBus<T> {
    protected final Class<T> eventClass;

    private final SortedSet<EventListener<T>> listeners = new TreeSet<>();
    
    public SimpleEventBus() {
        this.eventClass = (Class<T>) SimpleEventBus.class.getTypeParameters()[0].getBounds()[0];
    }
    
    @Override
    public <E extends T> E post(E event) {
        for (EventListener<T> listener : listeners) {
            listener.handleEvent(event);
        }
        
        return event;
    }

    @Override
    public void subscribe(Object listener) {
        //Get info from full class information if present
        SubscribeEvent fullClassInfo = ReflectionHelper.getClassAnnotation(listener.getClass(), SubscribeEvent.class);
        EventPriority defaultPriority = fullClassInfo != null ? fullClassInfo.priority() : EventPriority.NORMAL;
        boolean defaultIgnoreCancelled = fullClassInfo != null && fullClassInfo.ignoreCancelled();
        
        //Get all methods of the class, including parent methods
        Set<Method> methods = ReflectionHelper.getClassMethods(listener.getClass());
        //Remove methods that do not have a parameter count of 1
        methods.removeIf(method -> method.getParameterCount() != 1);
        
        for (Method method : methods) {
            //Use recursive reflection to get the SubscribeEvent annotation from the method
            SubscribeEvent methodAnnotation = ReflectionHelper.getMethodAnnotation(method, SubscribeEvent.class);
            //If the annotation is not present and no annotation exists on the class, ignore the method
            if (methodAnnotation == null && fullClassInfo == null) {
                continue;
            }
            
            Class<? extends T> eventClass;
            if (this.eventClass.isAssignableFrom(method.getParameterTypes()[0])) {
                eventClass = (Class<? extends T>) method.getParameterTypes()[0];
            } else {
                continue;
            }
            
            EventListener<T> eventListener = new EventListener<>(
                    listener, eventClass, method, 
                    methodAnnotation != null ? methodAnnotation.priority() : defaultPriority, 
                    methodAnnotation != null ? methodAnnotation.ignoreCancelled() : defaultIgnoreCancelled
            );
            
            this.listeners.add(eventListener);
        }
    }

    @Override
    public void unsubscribe(Object object) {
        this.listeners.removeIf(listener -> Objects.equals(listener.listener, object));
    }
    
    //Class to handle events for each of the methods. The EventHandler logic will be moved up
    static class EventListener<E> implements Comparable<EventListener<?>> {
        private final Object listener;
        private final Class<? extends E> eventClass;
        private final Method method;
        private final EventPriority priority;
        private final boolean ignoreCancelled;
        
        public EventListener(Object listener, Class<? extends E> eventClass, Method method, EventPriority priority, boolean ignoreCancelled) {
            this.listener = listener;
            this.eventClass = eventClass;
            this.method = method;
            this.priority = priority;
            this.ignoreCancelled = ignoreCancelled;
        }
        
        public void handleEvent(Object event) {
            if (event instanceof ICancellable cancellable) {
                if (cancellable.isCancelled()) {
                    return;
                }
            }
            
            if (eventClass.isAssignableFrom(event.getClass())) {
                try {
                    method.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            
            EventListener<?> that = (EventListener<?>) object;
            return Objects.equals(listener, that.listener) && Objects.equals(eventClass, that.eventClass) && Objects.equals(method, that.method);
        }
        
        @Override
        public int hashCode() {
            int result = Objects.hashCode(listener);
            result = 31 * result + Objects.hashCode(eventClass);
            result = 31 * result + Objects.hashCode(method);
            return result;
        }
        
        @Override
        public int compareTo(EventListener<?> o) {
            if (o == null) {
                return 1;
            }
            
            return Integer.compare(o.priority.ordinal(), priority.ordinal());
        }
    }

    static class EventHandler<E> {
        private Object listener;
        private Map<Class<? extends E>, Method> handlerMethods = new HashMap<>();
        private EventPriority priority;
        private boolean ignoreCancelled;

        public EventHandler(Object listener) {
            this.listener = listener;
            searchClass(this.listener.getClass());
        }
        
        protected boolean searchForFullClassListener(Class<?> listenerClass) {
            if (listenerClass.isAnnotationPresent(SubscribeEvent.class)) {
                return true;
            }
            
            for (Class<?> classInterface : listenerClass.getInterfaces()) {
                if (classInterface.isAnnotationPresent(SubscribeEvent.class)) {
                    return true;
                }
            }
            
            if (!listenerClass.getSuperclass().equals(Object.class)) {
                return searchForFullClassListener(listenerClass.getSuperclass());
            }
            
            return false;
        }
        
        protected void findAllListenerMethods(boolean fullClassListener, Class<?> listenerClass) {
            SubscribeEvent fullClassInfo;
            if (fullClassListener) {
                fullClassInfo = listenerClass.getAnnotation(SubscribeEvent.class);
            } else {
                fullClassInfo = null;
            }
            
            for (Method method : listenerClass.getDeclaredMethods()) {
                boolean methodListener = method.isAnnotationPresent(SubscribeEvent.class);
                if (fullClassListener || methodListener) {
                    SubscribeEvent methodInfo;
                    if (methodListener) {
                        methodInfo = method.getAnnotation(SubscribeEvent.class);
                    } else {
                        methodInfo = null;
                    }
                    
                    if (fullClassInfo != null) {
                        this.priority = fullClassInfo.priority();
                        this.ignoreCancelled = fullClassInfo.ignoreCancelled();
                    }
                    
                    if (methodInfo != null) {
                        this.priority = methodInfo.priority();
                        this.ignoreCancelled = methodInfo.ignoreCancelled();
                    }
                    
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length == 1) {
                        method.setAccessible(true);
                        try {
                            handlerMethods.put((Class<? extends E>) parameters[0].getType(), method);
                        } catch (Exception e) {}
                    }
                }
            }
            
            if (!listenerClass.getSuperclass().equals(Object.class)) {
                findAllListenerMethods(fullClassListener, listenerClass.getSuperclass());
            }
        }
        
        protected void searchClass(Class<?> listenerClass) {
            boolean fullClassListener = searchForFullClassListener(listenerClass);
            findAllListenerMethods(fullClassListener, listenerClass);
        }

        public Object getListener() {
            return listener;
        }

        public void handle(Object event) {
            for (Class<?> parameterClazz : handlerMethods.keySet()) {
                if (parameterClazz.isAssignableFrom(event.getClass())) {
                    Method method = handlerMethods.get(parameterClazz);
                    try {
                        method.invoke(listener, event);
                    } catch (Exception e) {
                        throw new RuntimeException("Could not pass " + event.getClass().getName() + " to " + listener.getClass().getName() + "." + method.getName(), e);
                    }
                }
            }
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            EventHandler<?> that = (EventHandler<?>) object;
            return Objects.equals(listener, that.listener);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(listener);
        }
    }
}
