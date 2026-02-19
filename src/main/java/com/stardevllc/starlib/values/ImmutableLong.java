package com.stardevllc.starlib.values;

public final class ImmutableLong implements Value<Long> {
    
    public static ImmutableLong of(long value) {
        return new ImmutableLong(value);
    }
    
    private final long value;
    
    public ImmutableLong(long value) {
        this.value = value;
    }
    
    public long get() {
        return value;
    }
    
    @Override
    public Long getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Long v) {
            return value == v;
        }
        
        return this == obj;
    }
}