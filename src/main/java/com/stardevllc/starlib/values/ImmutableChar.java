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
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Character v) {
            return value == v;
        }
        
        return this == obj;
    }
}