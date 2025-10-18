package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableIntegerValue;

public class SimpleIntegerValue implements WritableIntegerValue {
    
    protected int value;
    
    public SimpleIntegerValue() {
    }
    
    public SimpleIntegerValue(int value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int get() {
        return this.value;
    }
}
