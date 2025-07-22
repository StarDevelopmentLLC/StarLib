package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableBooleanValue;

/**
 * Represents a constant Boolean value
 */
public class BooleanConstant implements ObservableBooleanValue {
    
    private final boolean value;
    
    /**
     * Constructs a new Boolean constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public BooleanConstant(boolean value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean get() {
        return value;
    }
}