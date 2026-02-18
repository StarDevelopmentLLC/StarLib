package com.stardevllc.starlib.values;

public class MutableBoolean implements MutableValue<Boolean> {
    
    public static MutableBoolean of() {
        return new MutableBoolean();
    }
    
    public static MutableBoolean of(boolean value) {
        return new MutableBoolean(value);
    }
    
    protected boolean value;
    
    public MutableBoolean() {}
    
    public MutableBoolean(boolean value) {
        this.value = value;
    }
    
    public void set(boolean value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Boolean value) {
        set(value);
    }
    
    public boolean get() {
        return value;
    }
    
    @Override
    public Boolean getValue() {
        return get();
    }
}
