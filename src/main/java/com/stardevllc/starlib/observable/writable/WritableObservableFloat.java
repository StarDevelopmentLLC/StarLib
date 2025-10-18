package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.value.ObservableFloat;

/**
 * Represents a Writable Float Observable value
 */
public interface WritableObservableFloat extends ObservableFloat, WritableObservableNumber<Float> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(float value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Float value) {
        set(value);
    }
}