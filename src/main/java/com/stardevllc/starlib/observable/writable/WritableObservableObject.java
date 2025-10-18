package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableObservableValue;
import com.stardevllc.starlib.observable.value.ObservableObject;

/**
 * Represents a Writable Object Observable value
 * 
 * @param <T> The Object type
 */
public interface WritableObservableObject<T> extends ObservableObject<T>, WritableObservableValue<T> {
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