package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableLongValue;

public class SimpleLongValue implements WritableLongValue {
    
    protected long value;
    
    public SimpleLongValue() {
    }
    
    public SimpleLongValue(long value) {
        this.value = value;
    }
    
    @Override
    public void set(long value) {
        this.value = value;
    }
    
    @Override
    public long get() {
        return this.value;
    }
}
