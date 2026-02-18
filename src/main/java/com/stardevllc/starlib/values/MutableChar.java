package com.stardevllc.starlib.values;

public class MutableChar implements MutableValue<Character> {
    
    public static MutableChar of() {
        return new MutableChar();
    }
    
    public static MutableChar of(char value) {
        return new MutableChar(value);
    }
    
    protected char value;
    
    public MutableChar() {}
    
    public MutableChar(char value) {
        this.value = value;
    }
    
    public void set(char value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Character value) {
        set(value);
    }
    
    public char get() {
        return value;
    }
    
    @Override
    public Character getValue() {
        return get();
    }
}
