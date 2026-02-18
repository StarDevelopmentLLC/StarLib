package com.stardevllc.starlib.values;

public final class ImmutableChar implements Value<Character> {
    
    public static ImmutableChar of(char value) {
        return new ImmutableChar(value);
    }
    
    private final char value;
    
    public ImmutableChar(char value) {
        this.value = value;
    }
    
    public char get() {
        return value;
    }
    
    @Override
    public Character getValue() {
        return get();
    }
}