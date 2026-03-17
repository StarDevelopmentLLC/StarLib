package com.stardevllc.starlib.event.bus;

import com.stardevllc.starlib.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

/**
 * Represents an Event Bus that uses reflection
 */
public class ReflectionEventBus implements IEventBus {
    private static final Set<String> objectMethods = new HashSet<>();
    
    static {
        for (Method classMethod : ReflectionHelper.getClassMethods(Object.class)) {
            objectMethods.add(classMethod.getName().toLowerCase());
        }
    }
    
    protected final Set<Class<?>> eventTypes = new HashSet<>();
    protected final Set<IEventBus> childBusses = new HashSet<>();
    protected final Map<Class<?>, Predicate<?>> cancelHandlers = new HashMap<>();
    protected final Set<EventHandler> handlers = new TreeSet<>();
    
    public ReflectionEventBus() {
    }
    
    public ReflectionEventBus(Class<?> eventType) {
        this.eventTypes.add(eventType);
    }
    
    public ReflectionEventBus(Collection<Class<?>> eventTypes) {
        if (eventTypes != null) {
            this.eventTypes.addAll(eventTypes);
        }
    }
    
    public ReflectionEventBus(Class<?>... eventTypes) {
        if (eventTypes != null) {
            this.eventTypes.addAll(List.of(eventTypes));
        }
    }
    
    protected void callListeners(Object event) {
        for (EventHandler listener : handlers) {
            listener.handleEvent(event);
        }
    }
    
    protected void handleChildBusses(Object event) {
        for (IEventBus cb : childBusses) {
            cb.post(event);
        }
    }
    
    @Override
    public <E> E post(E event) {
        callListeners(event);
        handleChildBusses(event);
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
            
            //Check to see if the parameter is valid for this event bus
            Class<?> parameterType = method.getParameterTypes()[0];
            boolean valid = false;
            if (this.eventTypes.isEmpty() || this.eventTypes.contains(Object.class)) {
                valid = true;
            } else {
                for (Class<?> eventType : this.eventTypes) {
                    if (eventType.isAssignableFrom(parameterType)) {
                        valid = true;
                        break;
                    }
                }
            }
            
            if (!valid) {
                return false;
            }
            
            method.setAccessible(true);
            
            EventHandler eventListener = new EventHandler(
                    this, listener, parameterType, method,
                    methodAnnotation != null ? methodAnnotation.priority() : defaultPriority,
                    methodAnnotation != null ? methodAnnotation.ignoreCancelled() : defaultIgnoreCancelled
            );
            
            return this.handlers.add(eventListener);
        }
        
        return false;
    }
    
    @Override
    public boolean unsubscribe(Object object) {
        return this.handlers.removeIf(listener -> Objects.equals(listener.listener, object));
    }
    
    @Override
    public void clearListeners() {
        this.handlers.clear();
    }
    
    @Override
    public Set<Class<?>> getEventClasses() {
        return new HashSet<>(eventTypes);
    }
    
    @Override
    public <E> void addEventType(Class<E> eventClass) {
        eventTypes.add(eventClass);
    }
    
    @Override
    public void addChildBus(IEventBus childBus) {
        this.childBusses.add(childBus);
    }
    
    @Override
    public <C> void addCancelHandler(Class<C> cancellableClass, Predicate<C> predicate) {
        this.cancelHandlers.put(cancellableClass, predicate);
    }
    
    @Override
    public Set<Class<?>> getCancellableClasses() {
        return new HashSet<>(cancelHandlers.keySet());
    }
    
    //Class to handle events for each of the methods.
    public static class EventHandler implements Comparable<EventHandler> {
        private final ReflectionEventBus bus;
        private final Object listener;
        private final Class<?> eventClass;
        private final Method method;
        private final EventPriority priority;
        private final boolean ignoreCancelled;
        
        public EventHandler(ReflectionEventBus bus, Object listener, Class<?> eventClass, Method method, EventPriority priority, boolean ignoreCancelled) {
            this.bus = bus;
            this.listener = listener;
            this.eventClass = eventClass;
            this.method = method;
            this.priority = priority;
            this.ignoreCancelled = ignoreCancelled;
        }
        
        public void handleEvent(Object event) {
            boolean cancelled = false;
            if (!bus.cancelHandlers.isEmpty()) {
                for (Map.Entry<Class<?>, Predicate<?>> entry : bus.cancelHandlers.entrySet()) {
                    try {
                        Class<?> clazz = entry.getKey();
                        Predicate<Object> condition = (Predicate<Object>) entry.getValue();
                        if (clazz.isInstance(event)) {
                            if (condition.test(event)) {
                                cancelled = true;
                                break;
                            }
                        }
                    } catch (Throwable t) {
                    }
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
            
            EventHandler that = (EventHandler) object;
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
        public int compareTo(EventHandler o) {
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
