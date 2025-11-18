package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableDouble;

public class SimpleObservableDouble implements WritableObservableDouble {
    
    private final ListenerHandler<Double> handler = new ListenerHandler<>();
    private double value;
    
    public SimpleObservableDouble() {
    }
    
    public SimpleObservableDouble(double value) {
        this.value = value;
    }
    
    @Override
    public void set(double value) {
        double oldValue = this.value;
        if (!handler.handleChange(this, oldValue, value)) {
            this.value = value;
        }
    }
    
    @Override
    public ListenerHandler<Double> getHandler() {
        return handler;
    }
    
    @Override
    public double get() {
        return value;
    }
}
