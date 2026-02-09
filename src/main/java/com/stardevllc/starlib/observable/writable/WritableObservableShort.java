package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.value.ObservableShort;

/**
 * Represents a Writable Short Observable value
 */
public interface WritableObservableShort extends ObservableShort, WritableObservableNumber<Short> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(short value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Short value) {
        set(value);
    }
}