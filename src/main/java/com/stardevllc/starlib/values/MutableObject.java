package com.stardevllc.starlib.values;

public class MutableObject<T> implements MutableValue<T> {
    
    public static <T> MutableObject<T> of() {
        return new MutableObject<>();
    }
    
    public static <T> MutableObject<T> of(T value) {
        return new MutableObject<>(value);
    }
    
    protected T value;
    
    public MutableObject() {}
    
    public MutableObject(T value) {
        this.value = value;
    }
    
    public void set(T value) {
        this.value = value;
    }
    
    @Override
    public void setValue(T value) {
        set(value);
    }
    
    public T get() {
        return value;
    }
    
    @Override
    public T getValue() {
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
