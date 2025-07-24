package com.stardevllc.starlib.eventbus.impl;

import com.stardevllc.starlib.eventbus.*;
import com.stardevllc.starlib.helper.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;

public class SimpleEventBus<T> implements IEventBus<T> {
    protected final Class<T> eventClass;

    private final SortedSet<EventListener<T>> listeners = new TreeSet<>();
    
    private final List<IEventBus<?>> childBusses = new ArrayList<>();
    
    public SimpleEventBus() {
        this.eventClass = (Class<T>) SimpleEventBus.class.getTypeParameters()[0].getBounds()[0];
    }
    
    @Override
    public <E extends T> E post(E event) {
        for (EventListener<T> listener : listeners) {
            listener.handleEvent(event);
        }
        
        for (IEventBus<?> cb : childBusses) {
            if (cb.getEventClass().isAssignableFrom(getEventClass())) {
                ((IEventBus<T>) cb).post(event);
            }
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
            
            method.setAccessible(true);
            
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
    
    @Override
    public void addChildBus(IEventBus<?> childBus) {
        this.childBusses.add(childBus);
    }
    
    @Override
    public Class<T> getEventClass() {
        return eventClass;
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
}
