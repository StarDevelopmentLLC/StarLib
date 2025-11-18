package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableLong;

public class SimpleObservableLong implements WritableObservableLong {
    
    private final ListenerHandler<Long> handler = new ListenerHandler<>();
    private long value;
    
    public SimpleObservableLong() {
    }
    
    public SimpleObservableLong(long value) {
        this.value = value;
    }
    
    @Override
    public void set(long value) {
        long oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Long> getHandler() {
        return handler;
    }
    
    @Override
    public long get() {
        return value;
    }
}