package com.stardevllc.starlib.values;

public class MutableFloat implements MutableValue<Float> {
    
    public static MutableFloat of() {
        return new MutableFloat();
    }
    
    public static MutableFloat of(float value) {
        return new MutableFloat(value);
    }
    
    protected float value;
    
    public MutableFloat() {}
    
    public MutableFloat(float value) {
        this.value = value;
    }
    
    public void set(float value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Float value) {
        set(value);
    }
    
    public float get() {
        return value;
    }
    
    @Override
    public Float getValue() {
        return get();
    }
}
