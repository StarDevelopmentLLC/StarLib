package com.stardevllc.observable;

/**
 * Represents an ObserableValue that can be written to directly
 *
 * @param <T> The value tyupe
 */
public interface WritableValue<T> extends ObservableValue<T> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void setValue(T value);
}