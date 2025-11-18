package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableObject;

public class SimpleObservableObject<T> implements WritableObservableObject<T> {
    
    private final ListenerHandler<T> handler = new ListenerHandler<>();
    private T value;
    
    public SimpleObservableObject() {
    }
    
    public SimpleObservableObject(T value) {
        this.value = value;
    }
    
    @Override
    public void set(T value) {
        T oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<T> getHandler() {
        return handler;
    }
    
    @Override
    public T get() {
        return value;
    }
}
