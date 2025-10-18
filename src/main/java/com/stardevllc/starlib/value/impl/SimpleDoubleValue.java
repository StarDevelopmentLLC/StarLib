package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableDoubleValue;

public class SimpleDoubleValue implements WritableDoubleValue {
    protected double value;
    
    public SimpleDoubleValue() {
    }
    
    public SimpleDoubleValue(double value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(double value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double get() {
        return this.value;
    }
}
