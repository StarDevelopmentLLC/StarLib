package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableFloatValue;

public class SimpleFloatValue implements WritableFloatValue {
    
    protected float value;
    
    public SimpleFloatValue() {
    }
    
    public SimpleFloatValue(float value) {
        this.value = value;
    }
    
    @Override
    public void set(float value) {
        this.value = value;
    }
    
    @Override
    public float get() {
        return this.value;
    }
}
