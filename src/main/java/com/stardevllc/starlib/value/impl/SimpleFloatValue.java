package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableFloatValue;

@Deprecated(since = "0.24.0")
public class SimpleFloatValue implements WritableFloatValue {
    
    protected float value;
    
    public SimpleFloatValue() {
    }
    
    public SimpleFloatValue(float value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(float value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float get() {
        return this.value;
    }
}
