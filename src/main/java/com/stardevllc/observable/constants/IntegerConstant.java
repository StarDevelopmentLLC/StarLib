package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableIntegerValue;

/**
 * Represents a constant Integer value
 */
public class IntegerConstant implements ObservableIntegerValue {
    
    private final int value;
    
    /**
     * Constructs a new Integer constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public IntegerConstant(final int value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int get() {
        return value;
    }
}
