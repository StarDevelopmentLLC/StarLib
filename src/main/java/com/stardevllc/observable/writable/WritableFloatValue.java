package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableFloatValue;

/**
 * Represents a Writable Float Observable value
 */
public interface WritableFloatValue extends ObservableFloatValue, WritableNumberValue<Float> {
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