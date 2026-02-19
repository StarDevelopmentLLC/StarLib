package com.stardevllc.starlib.values;

public final class ImmutableDouble implements Value<Double> {
    
    public static ImmutableDouble of(double value) {
        return new ImmutableDouble(value);
    }
    
    private final double value;
    
    public ImmutableDouble(double value) {
        this.value = value;
    }
    
    public double get() {
        return value;
    }
    
    @Override
    public Double getValue() {
        return get();
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Double v) {
            return value == v;
        }
        
        return this == obj;
    }
}