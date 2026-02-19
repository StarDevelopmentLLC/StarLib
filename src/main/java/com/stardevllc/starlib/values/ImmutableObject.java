package com.stardevllc.starlib.values;

@SuppressWarnings("ClassCanBeRecord")
public final class ImmutableObject<T> implements Value<T> {
    
    public static <T> ImmutableObject<T> of(T value) {
        return new ImmutableObject<>(value);
    }
    
    private final T value;
    
    public ImmutableObject(T value) {
        this.value = value;
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