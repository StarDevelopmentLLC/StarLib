package com.stardevllc.starlib.eventbus.impl;

import com.stardevllc.starlib.eventbus.*;
import com.stardevllc.starlib.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;

/**
 * This is a complete implementation of {@link IEventBus} <br>
 * It is not required that this implementation is used and mainly an example and for code reuse among StarDevLLC Projects
 *
 * @param <T> The event type
 */
public class StarEventBus<T> implements IEventBus<T> {
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
    private final Set<EventHandler<T>> handlers = new TreeSet<>();
    
    /**
     * All child busses to this event bus
     */
    private final List<IEventBus<?>> childBusses = new ArrayList<>();
    
    protected Map<Class<?>, Predicate<?>> cancelMappers = new HashMap<>();
    
    /**
     * Constructs a simple event bus that detects the event type (Might not work depending on context)
     */
    public StarEventBus() {
        this((Class<T>) StarEventBus.class.getTypeParameters()[0].getBounds()[0]);
    }
    
    /**
     * Constructs a simple event bus with a specified event class
     *
     * @param eventClass The event class
     */
    public StarEventBus(Class<T> eventClass) {
        this.eventClass = eventClass;
    }
    
    @Override
    public void clearListeners() {
        this.handlers.clear();
    }
    
    @Override
    public <E extends T> E post(E event) {
        for (EventHandler<T> listener : handlers) {
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
    public <C> void addCancelHandler(Class<C> cancellableClass, Predicate<C> mapper) {
        this.cancelMappers.put(cancellableClass, mapper);
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
            
            EventHandler<T> eventListener = new EventHandler<>(
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
    public void addChildBus(IEventBus<?> childBus) {
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
    public Set<EventHandler<T>> getHandlers() {
        return new TreeSet<>(handlers);
    }
    
    //Class to handle events for each of the methods.
    public static class EventHandler<E> implements Comparable<EventHandler<?>> {
        private final StarEventBus<E> bus;
        private final Object listener;
        private final Class<? extends E> eventClass;
        private final Method method;
        private final EventPriority priority;
        private final boolean ignoreCancelled;
        
        public EventHandler(StarEventBus<E> bus, Object listener, Class<? extends E> eventClass, Method method, EventPriority priority, boolean ignoreCancelled) {
            this.bus = bus;
            this.listener = listener;
            this.eventClass = eventClass;
            this.method = method;
            this.priority = priority;
            this.ignoreCancelled = ignoreCancelled;
        }
        
        public void handleEvent(Object event) {
            boolean cancelled = false;
            if (!bus.cancelMappers.isEmpty()) {
                for (Entry<Class<?>, Predicate<?>> entry : bus.cancelMappers.entrySet()) {
                    try {
                        Class<?> clazz = entry.getKey();
                        Predicate<Object> condition = (Predicate<Object>) entry.getValue();
                        if (clazz.isInstance(event)) {
                            if (condition.test(event)) {
                                cancelled = true;
                                break;
                            }
                        }
                    } catch (Throwable t) {}
                }
            }
            
            if (cancelled && !ignoreCancelled) {
                return;
            }
            
            if (eventClass.isInstance(event)) {
                try {
                    method.invoke(listener, event);
                } catch (Exception e) {
                }
            }
        }
        
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            
            EventHandler<?> that = (EventHandler<?>) object;
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
        public int compareTo(EventHandler<?> o) {
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