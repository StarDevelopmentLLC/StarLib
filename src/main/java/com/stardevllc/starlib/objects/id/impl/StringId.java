package com.stardevllc.starlib.objects.id.impl;

import com.stardevllc.starlib.objects.id.ID;
import org.jetbrains.annotations.NotNull;

public final class StringId implements ID {
    
    public static final StringId EMPTY = new StringId("");
    
    private final String value;
    
    public StringId(String value) {
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
        if (obj instanceof StringId sid) {
            return this.value.equals(sid.value);
        }
        
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int compareTo(@NotNull ID o) {
        if (o instanceof StringId sid) {
            return this.value.compareTo(sid.value);
        }
        
        return -1;
    }
}