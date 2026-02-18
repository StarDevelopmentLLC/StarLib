package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableBooleanValue;

@Deprecated(since = "0.24.0")
public class SimpleBooleanValue implements WritableBooleanValue {
    protected boolean value;
    
    public SimpleBooleanValue() {
    }
    
    public SimpleBooleanValue(boolean value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean get() {
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(boolean value) {
        this.value = value;
    }
}