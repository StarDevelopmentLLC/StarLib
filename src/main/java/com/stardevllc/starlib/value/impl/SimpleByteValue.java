package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableByteValue;

public class SimpleByteValue implements WritableByteValue {
    
    protected byte value;
    
    public SimpleByteValue() {
    }
    
    public SimpleByteValue(byte value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(byte value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte get() {
        return this.value;
    }
}
