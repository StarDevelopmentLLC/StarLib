package com.stardevllc.starlib.values;

public class MutableByte implements MutableValue<Byte> {
    
    public static MutableByte of() {
        return new MutableByte();
    }
    
    public static MutableByte of(byte value) {
        return new MutableByte(value);
    }
    
    protected byte value;
    
    public MutableByte() {}
    
    public MutableByte(byte value) {
        this.value = value;
    }
    
    public void set(byte value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Byte value) {
        set(value);
    }
    
    public byte get() {
        return value;
    }
    
    @Override
    public Byte getValue() {
        return get();
    }
}
