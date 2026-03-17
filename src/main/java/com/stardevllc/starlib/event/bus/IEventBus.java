package com.stardevllc.starlib.event.bus;

import com.stardevllc.starlib.event.EventDispatcher;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Predicate;

/**
 * This is a PubSub (Publish Subscribe) structure that allows listening for events to happen and publishing events to it
 */
public interface IEventBus extends EventDispatcher {
    /**
     * Posts an event to the bus, calling listeners
     *
     * @param event The event
     * @param <E>   The event type
     * @return The event
     */
    <E> E post(E event);
    
    @Override
    @NotNull
    default <E> E dispatch(E event) {
        return post(event);
    }
    
    /**
     * Subscribes to the bus for events
     *
     * @param object The listener object
     * @return If the subscription was successful
     */
    boolean subscribe(Object object);
    
    @Override
    default void addListener(Object listener) {
        subscribe(listener);
    }
    
    /**
     * Unsubscribes from the bus
     *
     * @param object The listener instance
     * @return If the unsubscription was successful
     */
    boolean unsubscribe(Object object);
    
    /**
     * Clears all listeners in the bus
     */
    default void clearListeners() {
        
    }
    
    /**
     * A copy of the set of classes for all events supported by this event bus, this can be null or empty meaning it supports all classes
     *
     * @return A copy of the set of supported event classes
     */
    default Set<Class<?>> getEventClasses() {
        return Set.of();
    }
    
    /**
     * Adds an event type to the supported set of events of this EventBus. This may do nothing depending on the implementation
     *
     * @param eventClass The class
     * @param <E>        The event type
     */
    default <E> void addEventType(Class<E> eventClass) {
        
    }
    
    /**
     * Adds a child bus to this one. The child's post method is called after this listeners are handled
     *
     * @param childBus The child bus
     */
    default void addChildBus(IEventBus childBus) {
        
    }
    
    /**
     * This set is a set of the classes that are cancellable that is supported by this event bus
     *
     * @return The set of cancellable classes
     */
    default Set<Class<?>> getCancellableClasses() {
        return Set.of();
    }
    
    /**
     * Sets the handler used to check to see if an event is cancelled
     *
     * @param cancellableClass The class used as the marker of a cancellable event
     * @param predicate        The predicate that checks an event instance to check
     */
    default <C> void addCancelHandler(Class<C> cancellableClass, Predicate<C> predicate) {
        
    }
}
