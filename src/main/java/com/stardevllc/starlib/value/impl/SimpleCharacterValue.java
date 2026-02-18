package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableCharacterValue;

@Deprecated(since = "0.24.0")
public class SimpleCharacterValue implements WritableCharacterValue {
    protected char value;
    
    public SimpleCharacterValue() {
    }
    
    public SimpleCharacterValue(char value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(char newValue) {
        this.value = newValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public char get() {
        return value;
    }
}
