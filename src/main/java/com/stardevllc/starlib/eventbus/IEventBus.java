package com.stardevllc.starlib.eventbus;

/**
 * Represents a bus where events are passed to and listeners are used to react to those events
 *
 * @param <T> The event type
 */
public interface IEventBus<T> {
    /**
     * Posts an event to the bus, calling listeners
     *
     * @param event The event
     * @param <E>   The event type
     * @return The event
     */
    <E extends T> E post(E event);
    
    /**
     * Subscribes to the bus for events
     *
     * @param object The listener object
     * @return If the subscription was successful
     */
    boolean subscribe(Object object);
    
    /**
     * Unsubscribes from the bus
     *
     * @param object The listener instance
     * @return If the unsubscription was successful
     */
    boolean unsubscribe(Object object);
    
    /**
     * The base event class
     *
     * @return The base event class
     */
    Class<T> getEventClass();
    
    /**
     * Clears all listeners in the bus
     */
    void clearListeners();
    
    /**
     * Adds a child bus to this one. The child's post method is called after this listeners are handled
     *
     * @param childBus The child bus
     */
    default void addChildBus(IEventBus<?> childBus) {
        
    }
}
