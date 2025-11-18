package com.stardevllc.starlib.observable.impl;

import com.stardevllc.starlib.observable.ListenerHandler;
import com.stardevllc.starlib.observable.writable.WritableObservableCharacter;

public class SimpleObservableCharacter implements WritableObservableCharacter {
    
    private final ListenerHandler<Character> handler = new ListenerHandler<>();
    private char value;
    
    public SimpleObservableCharacter() {
    }
    
    public SimpleObservableCharacter(char value) {
        this.value = value;
    }
    
    @Override
    public void set(char newValue) {
        char oldValue = this.value;
        if (!handler.handleChange(this, oldValue, newValue)) {
            this.value = newValue;
        }
    }
    
    @Override
    public ListenerHandler<Character> getHandler() {
        return handler;
    }
    
    @Override
    public char get() {
        return value;
    }
}
