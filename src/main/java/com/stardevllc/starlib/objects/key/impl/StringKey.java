package com.stardevllc.starlib.objects.key.impl;

import com.stardevllc.starlib.objects.key.Key;
import org.jetbrains.annotations.NotNull;

public final class StringKey implements Key {
    
    public static final StringKey EMPTY = new StringKey("");
    
    private final String value;
    
    public StringKey(String value) {
        this.value = value;
        if (this.value == null) {
            throw new IllegalArgumentException("Value cannot be null for a StringId");
        }
    }
    
    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringKey sid) {
            return this.value.equals(sid.value);
        }
        
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        if (o instanceof StringKey sid) {
            return this.value.compareTo(sid.value);
        }
        
        return -1;
    }
}