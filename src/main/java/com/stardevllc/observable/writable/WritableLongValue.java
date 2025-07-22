package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableLongValue;

/**
 * Represents a Writable Long Observable value
 */
public interface WritableLongValue extends ObservableLongValue, WritableNumberValue<Long> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(long value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Long value) {
        set(value);
    }
}