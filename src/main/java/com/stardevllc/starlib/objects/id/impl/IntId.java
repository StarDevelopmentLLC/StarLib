package com.stardevllc.starlib.objects.id.impl;

import com.stardevllc.starlib.objects.id.ID;
import org.jetbrains.annotations.NotNull;

public final class IntId implements ID {
    
    public static final IntId EMPTY = new IntId(0);
    
    private final int value;
    
    public IntId(int value) {
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
        if (obj instanceof IntId iid) {
            return this.value == iid.value;
        }
        
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
    
    @Override
    public int compareTo(@NotNull ID o) {
        if (o instanceof IntId iid) {
            return Integer.compare(this.value, iid.value);
        }
        
        return -1;
    }
}