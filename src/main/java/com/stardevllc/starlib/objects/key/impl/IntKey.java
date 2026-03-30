package com.stardevllc.starlib.objects.key.impl;

import com.stardevllc.starlib.objects.key.Key;
import org.jetbrains.annotations.NotNull;

public final class IntKey implements Key {
    
    public static final IntKey EMPTY = new IntKey(0);
    
    private final int value;
    
    public IntKey(int value) {
        this.value = value;
    }
    
    @Override
    public boolean isEmpty() {
        return value != 0;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntKey iid) {
            return this.value == iid.value;
        }
        
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        if (o instanceof IntKey iid) {
            return Integer.compare(this.value, iid.value);
        }
        
        return -1;
    }
}