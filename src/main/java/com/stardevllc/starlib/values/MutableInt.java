package com.stardevllc.starlib.values;

public class MutableInt implements MutableValue<Integer> {
    
    public static MutableInt of() {
        return new MutableInt();
    }
    
    public static MutableInt of(int value) {
        return new MutableInt(value);
    }
    
    protected int value;
    
    public MutableInt() {}
    
    public MutableInt(int value) {
        this.value = value;
    }
    
    public void set(int value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Integer value) {
        set(value);
    }
    
    public int get() {
        return value;
    }
    
    @Override
    public Integer getValue() {
        return get();
    }
}
