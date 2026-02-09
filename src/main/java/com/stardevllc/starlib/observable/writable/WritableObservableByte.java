package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.value.ObservableByte;

/**
 * Represents a Writable Integer Observable value
 */
public interface WritableObservableByte extends ObservableByte, WritableObservableNumber<Byte> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(byte value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Byte value) {
        set(value);
    }
}