package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.value.Value;

/**
 * An Observable that has a value which can be listened to for changes
 *
 * @param <T> The value type
 */
public interface ObservableValue<T> extends Observable, Value<T> {
    
    /**
     * Gets the handler instance for the change listeners
     *
     * @return The handler instance
     */
    ListenerHandler<T> getHandler();
    
    /**
     * Adds a change listener to this observable value
     *
     * @param listener The listener to add
     */
    default void addListener(ChangeListener<T> listener) {
        getHandler().addListener(listener);
    }
    
    /**
     * Removes a change listener to this observable value
     *
     * @param listener The listener to remove
     */
    default void removeListener(ChangeListener<T> listener) {
        getHandler().addListener(listener);
    }
}
