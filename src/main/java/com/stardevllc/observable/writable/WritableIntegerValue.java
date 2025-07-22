package com.stardevllc.observable.writable;

import com.stardevllc.observable.value.ObservableIntegerValue;

/**
 * Represents a Writable Integer Observable value
 */
public interface WritableIntegerValue extends ObservableIntegerValue, WritableNumberValue<Integer> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(int value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Integer value) {
        set(value);
    }
}