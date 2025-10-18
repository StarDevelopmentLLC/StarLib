package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.value.ObservableInteger;

/**
 * Represents a Writable Integer Observable value
 */
public interface WritableObservableInteger extends ObservableInteger, WritableObservableNumber<Integer> {
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