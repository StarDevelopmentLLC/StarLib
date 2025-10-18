package com.stardevllc.starlib.eventbus.impl;

import com.stardevllc.starlib.eventbus.*;
import com.stardevllc.starlib.helper.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This is a simple implementation of an {@link IEventBus}. It is not required to use this
 *
 * @param <T> The event type
 */
public class SimpleEventBus<T> implements IEventBus<T> {
    private static final Set<String> objectMethods = new HashSet<>();
    
    static {
        for (Method classMethod : ReflectionHelper.getClassMethods(Object.class)) {
            objectMethods.add(classMethod.getName().toLowerCase());
        }
    }
    
    /**
     * The base class  for the event type
     */
    protected final Class<T> eventClass;
    
    /**
     * All registered listeners of this bus
     */
    private final Set<EventListener<T>> listeners = new TreeSet<>();
    
    /**
     * All child busses to this event bus
     */
    private final List<IEventBus<?>> childBusses = new ArrayList<>();
    
    /**
     * Constructs a simple event bus that detects the event type (Might not work depending on context)
     */
    public SimpleEventBus() {
        this((Class<T>) SimpleEventBus.class.getTypeParameters()[0].getBounds()[0]);
    }
    
    /**
     * Constructs a simple event bus with a specified event class
     *
     * @param eventClass The event class
     */
    public SimpleEventBus(Class<T> eventClass) {
        this.eventClass = eventClass;
    }
    
    @Override
    public void clearListeners() {
        this.listeners.clear();
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
    public boolean subscribe(Object listener) {
        //Get info from full class information if present
        SubscribeEvent fullClassInfo = ReflectionHelper.getClassAnnotation(listener.getClass(), SubscribeEvent.class);
        EventPriority defaultPriority = fullClassInfo != null ? fullClassInfo.priority() : EventPriority.NORMAL;
        boolean defaultIgnoreCancelled = fullClassInfo != null && fullClassInfo.ignoreCancelled();
        
        //Get all methods of the class, including parent methods
        Set<Method> methods = ReflectionHelper.getClassMethods(listener.getClass());
        //Remove methods that do not have a parameter count of 1
        methods.removeIf(method -> method.getParameterCount() != 1);
        methods.removeIf(method -> objectMethods.contains(method.getName().toLowerCase()));
        
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
        
        return !listeners.isEmpty();
    }
    
    @Override
    public boolean unsubscribe(Object object) {
        return this.listeners.removeIf(listener -> Objects.equals(listener.listener, object));
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
                if (cancellable.isCancelled() && !ignoreCancelled) {
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
        
        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compareTo(EventListener<?> o) {
            if (o == null) {
                return 1;
            }
            
            if (o.priority.ordinal() == priority.ordinal()) {
                return -1;
            }
            
            return Integer.compare(o.priority.ordinal(), priority.ordinal());
        }
    }
}