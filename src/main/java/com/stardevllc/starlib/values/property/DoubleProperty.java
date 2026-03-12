package com.stardevllc.starlib.values.property;

public class DoubleProperty extends AbstractProperty<Double> {
    
    private double value;
    
    public DoubleProperty() {
        super(double.class);
    }
    
    public DoubleProperty(double value) {
        super(double.class);
        this.value = value;
    }
    
    public DoubleProperty(Object bean, String name) {
        super(bean, double.class, name);
    }
    
    public DoubleProperty(Object bean, String name, double value) {
        super(bean, double.class, name);
        this.value = value;
    }
    
    public void set(double value) {
        checkValid();
        if (isBound()) {
            return;
        }
        
        if (this.value != value) {
            fireChangeListeners(this.value, value);
        }
        
        this.value = value;
    }
    
    @Override
    public void setValue(Double value) {
        set(value);
    }
    
    public double get() {
        checkValid();
        if (isBound()) {
            return getBoundValue().getValue();
        }
        
        return this.value;
    }
    
    @Override
    public Double getValue() {
        return get();
    }
    
    @Override
    public final boolean equals(Object object) {
        checkValid();
        if (!(object instanceof DoubleProperty that)) {
            return false;
        }
        
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        checkValid();
        return Double.hashCode(this.value);
    }
    
    @Override
    public String toString() {
        checkValid();
        return "DoubleProperty{" +
                "value=" + value +
                '}';
    }
}
