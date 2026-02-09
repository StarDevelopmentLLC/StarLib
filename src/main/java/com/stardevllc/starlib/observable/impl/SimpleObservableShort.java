package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableShort;

public class SimpleObservableShort implements WritableObservableShort {
    
    private final ListenerHandler<Short> handler = new ListenerHandler<>();
    private short value;
    
    public SimpleObservableShort() {
    }
    
    public SimpleObservableShort(byte value) {
        this.value = value;
    }
    
    @Override
    public void set(short value) {
        short oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Short> getHandler() {
        return handler;
    }
    
    @Override
    public short get() {
        return value;
    }
}