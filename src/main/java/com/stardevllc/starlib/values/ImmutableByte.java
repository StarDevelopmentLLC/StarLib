package com.stardevllc.starlib.values;

public final class ImmutableByte implements Value<Byte> {
    
    public static ImmutableByte of(byte value) {
        return new ImmutableByte(value);
    }
    
    private final byte value;
    
    public ImmutableByte(byte value) {
        this.value = value;
    }
    
    public byte get() {
        return value;
    }
    
    @Override
    public Byte getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Byte v) {
            return value == v;
        }
        
        return this == obj;
    }
}