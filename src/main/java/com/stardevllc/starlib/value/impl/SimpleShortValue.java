package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableShortValue;

public class SimpleShortValue implements WritableShortValue {
    
    protected short value;
    
    public SimpleShortValue() {
    }
    
    public SimpleShortValue(short value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(short value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short get() {
        return this.value;
    }
}
