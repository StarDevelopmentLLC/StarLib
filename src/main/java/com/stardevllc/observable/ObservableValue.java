package com.stardevllc.observable;

/**
 * An Observable that has a value which can be listened to for changes
 *
 * @param <T> The value type
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableValue<T> extends Observable {
    
    /**
     * Adds a change listener to this observable value
     *
     * @param listener The listener to add
     */
    default void addListener(ChangeListener<? super T> listener) {}
    
    /**
     * Removes a change listener to this observable value
     *
     * @param listener The listener to remove
     */
    default void removeListener(ChangeListener<? super T> listener) {}
    
    /**
     * Gets the value that this ObservableValue wraps
     *
     * @return The value
     */
    T getValue();
}
