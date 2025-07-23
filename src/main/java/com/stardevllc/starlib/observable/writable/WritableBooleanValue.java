package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableValue;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;

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