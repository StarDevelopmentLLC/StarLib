package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableValue;
import com.stardevllc.starlib.observable.value.ObservableObjectValue;

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