package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableByte;

public class SimpleObservableByte implements WritableObservableByte {
    
    private final ListenerHandler<Byte> handler = new ListenerHandler<>();
    private byte value;
    
    public SimpleObservableByte() {
    }
    
    public SimpleObservableByte(byte value) {
        this.value = value;
    }
    
    @Override
    public void set(byte value) {
        byte oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Byte> getHandler() {
        return handler;
    }
    
    @Override
    public byte get() {
        return value;
    }
}