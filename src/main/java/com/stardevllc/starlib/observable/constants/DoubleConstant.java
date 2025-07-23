package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.observable.value.ObservableDoubleValue;

/**
 * Represents a constant Double value
 */
public class DoubleConstant implements ObservableDoubleValue {
    
    private final double value;
    
    /**
     * Constructs a new Double constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public DoubleConstant(final double value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double get() {
        return value;
    }
}
