package com.stardevllc.starlib.values;

public final class ImmutableBoolean implements Value<Boolean> {
    
    public static final ImmutableBoolean TRUE = new ImmutableBoolean(true);
    public static final ImmutableBoolean FALSE = new ImmutableBoolean(false);
    
    public static ImmutableBoolean of(boolean value) {
        return new ImmutableBoolean(value);
    }
    
    private final boolean value;
    
    public ImmutableBoolean(boolean value) {
        this.value = value;
    }
    
    public boolean get() {
        return value;
    }
    
    @Override
    public Boolean getValue() {
        return get();
    }
}