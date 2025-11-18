package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableFloat;

public class SimpleObservableFloat implements WritableObservableFloat {
    
    private final ListenerHandler<Float> handler = new ListenerHandler<>();
    private float value;
    
    public SimpleObservableFloat() {
    }
    
    public SimpleObservableFloat(float value) {
        this.value = value;
    }
    
    @Override
    public void set(float value) {
        float oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Float> getHandler() {
        return handler;
    }
    
    @Override
    public float get() {
        return value;
    }
}
