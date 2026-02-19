package com.stardevllc.starlib.values;

public final class ImmutableShort implements Value<Short> {
    
    public static ImmutableShort of(short value) {
        return new ImmutableShort(value);
    }
    
    private final short value;
    
    public ImmutableShort(short value) {
        this.value = value;
    }
    
    public short get() {
        return value;
    }
    
    @Override
    public Short getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Short v) {
            return value == v;
        }
        
        return this == obj;
    }
}