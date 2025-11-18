package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableInteger;

public class SimpleObservableInteger implements WritableObservableInteger {
    
    private final ListenerHandler<Integer> handler = new ListenerHandler<>();
    private int value;
    
    public SimpleObservableInteger() {
    }
    
    public SimpleObservableInteger(int value) {
        this.value = value;
    }
    
    @Override
    public void set(int value) {
        int oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Integer> getHandler() {
        return handler;
    }
    
    @Override
    public int get() {
        return value;
    }
}