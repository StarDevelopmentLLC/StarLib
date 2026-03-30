package com.stardevllc.starlib.objects.key.impl;

import com.stardevllc.starlib.objects.key.Key;
import org.jetbrains.annotations.NotNull;

public final class LongKey implements Key {
    
    public static final LongKey EMPTY = new LongKey(0);
    
    private final long value;
    
    public LongKey(long value) {
        this.value = value;
    }
    
    @Override
    public boolean isEmpty() {
        return value != 0;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LongKey iid) {
            return this.value == iid.value;
        }
        
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return Long.toString(this.value);
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        if (o instanceof LongKey iid) {
            return Long.compare(this.value, iid.value);
        }
        
        return -1;
    }
}