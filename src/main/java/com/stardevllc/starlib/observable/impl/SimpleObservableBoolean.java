package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableBoolean;

public class SimpleObservableBoolean implements WritableObservableBoolean {
    
    private final ListenerHandler<Boolean> handler = new ListenerHandler<>();
    private boolean value;
    
    public SimpleObservableBoolean() {
    }
    
    public SimpleObservableBoolean(boolean value) {
        this.value = value;
    }
    
    @Override
    public void set(boolean value) {
        boolean oldValue = this.value;
        if (!this.handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Boolean> getHandler() {
        return handler;
    }
    
    @Override
    public boolean get() {
        return value;
    }
}