package com.stardevllc.starlib.values;

public class MutableString implements MutableValue<String> {
    
    public static MutableString of() {
        return new MutableString();
    }
    
    public static MutableString of(String value) {
        return new MutableString(value);
    }
    
    protected String value;
    
    public MutableString() {}
    
    public MutableString(String value) {
        this.value = value;
    }
    
    public void set(String value) {
        this.value = value;
    }
    
    @Override
    public void setValue(String value) {
        set(value);
    }
    
    public String get() {
        return value;
    }
    
    @Override
    public String getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (value == obj) {
            return true;
        }
        
        if (value != null) {
            return value.equals(obj);
        }
        
        return false;
    }
}
