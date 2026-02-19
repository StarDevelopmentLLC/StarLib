package com.stardevllc.starlib.values;

public class MutableDouble implements MutableValue<Double> {
    
    public static MutableDouble of() {
        return new MutableDouble();
    }
    
    public static MutableDouble of(double value) {
        return new MutableDouble(value);
    }
    
    protected double value;
    
    public MutableDouble() {}
    
    public MutableDouble(double value) {
        this.value = value;
    }
    
    public void set(double value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Double value) {
        set(value);
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
