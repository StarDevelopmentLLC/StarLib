package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableLongValue;

/**
 * Represents a constant Long value
 */
public class LongConstant implements ObservableLongValue {
    
    private final long value;
    
    /**
     * Constructs a new Long constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public LongConstant(final long value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long get() {
        return value;
    }
}
