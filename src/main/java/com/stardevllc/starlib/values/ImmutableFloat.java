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
}