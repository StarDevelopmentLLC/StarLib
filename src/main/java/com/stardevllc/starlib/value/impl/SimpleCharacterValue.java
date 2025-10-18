package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableCharacterValue;

public class SimpleCharacterValue implements WritableCharacterValue {
    protected char value;
    
    public SimpleCharacterValue() {
    }
    
    public SimpleCharacterValue(char value) {
        this.value = value;
    }
    
    @Override
    public void set(char newValue) {
        this.value = newValue;
    }
    
    @Override
    public char get() {
        return value;
    }
}
