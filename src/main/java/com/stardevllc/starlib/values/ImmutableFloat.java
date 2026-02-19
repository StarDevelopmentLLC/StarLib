package com.stardevllc.starlib.values;

public final class ImmutableFloat implements Value<Float> {
    
    public static ImmutableFloat of(float value) {
        return new ImmutableFloat(value);
    }
    
    private final float value;
    
    public ImmutableFloat(float value) {
        this.value = value;
    }
    
    public float get() {
        return value;
    }
    
    @Override
    public Float getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Float v) {
            return value == v;
        }
        
        return this == obj;
    }
}