package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableObjectValue;

/**
 * Represents a Writable Object Observable value
 * 
 * @param <T> The Object type
 */
public interface WritableObjectValue<T> extends ObservableObjectValue<T>, WritableValue<T> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(T value);
    
    @Override
    default void setValue(T value) {
        set(value);
    }
}