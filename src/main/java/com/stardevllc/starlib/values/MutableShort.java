package com.stardevllc.starlib.values;

public class MutableShort implements MutableValue<Short> {
    
    public static MutableShort of() {
        return new MutableShort();
    }
    
    public static MutableShort of(short value) {
        return new MutableShort(value);
    }
    
    protected short value;
    
    public MutableShort() {}
    
    public MutableShort(short value) {
        this.value = value;
    }
    
    public void set(short value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Short value) {
        set(value);
    }
    
    public short get() {
        return value;
    }
    
    @Override
    public Short getValue() {
        return get();
    }
}
