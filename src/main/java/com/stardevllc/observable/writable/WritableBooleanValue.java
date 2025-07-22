package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableBooleanValue;

/**
 * Represents a Writable Boolean Observable value
 */
public interface WritableBooleanValue extends ObservableBooleanValue, WritableValue<Boolean> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(boolean value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Boolean value) {
        set(value);
    }
}