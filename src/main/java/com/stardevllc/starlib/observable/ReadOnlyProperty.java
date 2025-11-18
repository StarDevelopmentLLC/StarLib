package com.stardevllc.starlib.observable;

/**
 * Represents a Value that has an Identity
 *
 * @param <T> The value type
 */
public interface ReadOnlyProperty<T> extends Property<T>, ObservableValue<T> {
    /**
     * Binds this property to the value of an ObservableValue <br>
     * This method ignores the cancelled flag in change listeners
     *
     * @param other The value to bind to
     */
    void bind(ObservableValue<T> other);
    
    /**
     * Removes the binding (if any) <br>
     * This method ignores the cancelled flag in change listeners
     */
    void unbind();
}