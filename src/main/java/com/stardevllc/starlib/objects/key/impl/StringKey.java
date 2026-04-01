package com.stardevllc.starlib.objects.key.impl;

import com.stardevllc.starlib.objects.key.Key;
import org.jetbrains.annotations.NotNull;

public final class StringKey implements Key {
    
    public static final StringKey EMPTY = new StringKey("");
    
    private final String value;
    
    public StringKey(String value) {
        this.value = value;
        if (this.value == null) {
            throw new IllegalArgumentException("Value cannot be null for a StringKey");
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
        return value.equals(obj.toString());
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        return value.compareTo(o.toString());
    }
}