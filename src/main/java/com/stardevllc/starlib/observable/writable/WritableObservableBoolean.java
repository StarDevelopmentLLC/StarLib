package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableObservableValue;
import com.stardevllc.starlib.observable.value.ObservableBoolean;

/**
 * Represents a Writable Boolean Observable value
 */
public interface WritableObservableBoolean extends ObservableBoolean, WritableObservableValue<Boolean> {
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