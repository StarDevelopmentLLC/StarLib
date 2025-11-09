package com.stardevllc.starlib.eventbus.impl;

import com.stardevllc.starlib.eventbus.*;
import com.stardevllc.starlib.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

/**
 * This is a complete implementation of {@link IEventBus} <br>
 * It is not required that this implementation is used and mainly an example and for code reuse among StarDevLLC Projects
 *
 * @param <T> The event type
 * @param <C> The cancellable type
 */
public class StarEventBus<T, C> implements IEventBus<T, C> {
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
    private final Set<EventHandler<T, C>> handlers = new TreeSet<>();
    
    /**
     * All child busses to this event bus
     */
    private final List<IEventBus<?, ?>> childBusses = new ArrayList<>();
    
    /**
     * The class for the cancel check
     */
    protected Class<C> cancelClass;
    
    /**
     * The predicate for the cancel checker
     */
    protected Predicate<C> cancelMapper;
    
    /**
     * Constructs a simple event bus that detects the event type (Might not work depending on context)
     */
    public StarEventBus() {
        this((Class<T>) StarEventBus.class.getTypeParameters()[0].getBounds()[0], (Class<C>) StarEventBus.class.getTypeParameters()[1].getBounds()[0], null);
    }
    
    /**
     * Constructs a simple event bus with a specified event class
     *
     * @param eventClass The event class
     */
    public StarEventBus(Class<T> eventClass, Class<C> cancelClass, Predicate<C> cancelMapper) {
        this.eventClass = eventClass;
        this.cancelClass = cancelClass;
        this.cancelMapper = cancelMapper;
    }
    
    @Override
    public void clearListeners() {
        this.handlers.clear();
    }
    
    @Override
    public <E extends T> E post(E event) {
        for (EventHandler<T, C> listener : handlers) {
            listener.handleEvent(event);
        }
        
        for (IEventBus<?, ?> cb : childBusses) {
            if (cb.getEventClass().isAssignableFrom(getEventClass())) {
                ((IEventBus<T, C>) cb).post(event);
            }
        }
        
        return event;
    }
    
    @Override
    public void setCancelMapper(Predicate<C> cancelMapper) {
        if (cancelClass == null) {
            throw new IllegalStateException("No cancelClass specified. Use SimpleEventBus#setCancelHandler(Class, Predicate) instead");
        }
        this.cancelMapper = cancelMapper;
    }
    
    @Override
    public void setCancelHandler(Class<C> cancellableClass, Predicate<C> mapper) {
        this.cancelClass = cancellableClass;
        this.cancelMapper = mapper;
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
            
            EventHandler<T, C> eventListener = new EventHandler<>(
                    this, listener, eventClass, method,
                    methodAnnotation != null ? methodAnnotation.priority() : defaultPriority,
                    methodAnnotation != null ? methodAnnotation.ignoreCancelled() : defaultIgnoreCancelled
            );
            
            this.handlers.add(eventListener);
        }
        
        return !handlers.isEmpty();
    }
    
    @Override
    public boolean unsubscribe(Object object) {
        return this.handlers.removeIf(listener -> Objects.equals(listener.listener, object));
    }
    
    @Override
    public void addChildBus(IEventBus<?, ?> childBus) {
        this.childBusses.add(childBus);
    }
    
    @Override
    public Class<T> getEventClass() {
        return eventClass;
    }
    
    /**
     * Returns a copy of the handlers set
     *
     * @return The handlers registered to this event bus
     */
    public Set<EventHandler<T, C>> getHandlers() {
        return new TreeSet<>(handlers);
    }
    
    //Class to handle events for each of the methods.
    public static class EventHandler<E, C> implements Comparable<EventHandler<?, ?>> {
        private final StarEventBus<E, C> bus;
        private final Object listener;
        private final Class<? extends E> eventClass;
        private final Method method;
        private final EventPriority priority;
        private final boolean ignoreCancelled;
        
        public EventHandler(StarEventBus<E, C> bus, Object listener, Class<? extends E> eventClass, Method method, EventPriority priority, boolean ignoreCancelled) {
            this.bus = bus;
            this.listener = listener;
            this.eventClass = eventClass;
            this.method = method;
            this.priority = priority;
            this.ignoreCancelled = ignoreCancelled;
        }
        
        public void handleEvent(Object event) {
            if (bus.cancelClass != null) {
                if (bus.cancelClass.isInstance(event)) {
                    bus.cancelMapper.test((C) event);
                }
            }
            
            if (eventClass.isInstance(event)) {
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
            
            EventHandler<?, ?> that = (EventHandler<?, ?>) object;
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
        public int compareTo(EventHandler<?, ?> o) {
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