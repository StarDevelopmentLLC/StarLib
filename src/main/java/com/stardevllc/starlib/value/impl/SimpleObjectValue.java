package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableObjectValue;

public class SimpleObjectValue<T> implements WritableObjectValue<T> {
    
    protected T value;
    
    public SimpleObjectValue() {
    }
    
    public SimpleObjectValue(T value) {
        this.value = value;
    }
    
    @Override
    public void set(T value) {
        this.value = value;
    }
    
    @Override
    public T get() {
        return value;
    }
}
