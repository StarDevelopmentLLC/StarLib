package com.stardevllc.starlib.values;

public final class ImmutableInt implements Value<Integer> {
    
    public static ImmutableInt of(int value) {
        return new ImmutableInt(value);
    }
    
    private final int value;
    
    public ImmutableInt(int value) {
        this.value = value;
    }
    
    public int get() {
        return value;
    }
    
    @Override
    public Integer getValue() {
        return get();
    }
}