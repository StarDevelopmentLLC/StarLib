package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.ChangeEvent;
import com.stardevllc.starlib.observable.writable.WritableDoubleValue;

public class DoubleProperty extends NumberProperty<Double> implements WritableDoubleValue {
    
    protected double value;
    
    public DoubleProperty(Object bean, String name, double value) {
        super(bean, name);
        this.value = value;
    }
    
    public DoubleProperty(String name, double value) {
        this(null, name, value);
    }
    
    public DoubleProperty(double value) {
        this(null, "", value);
    }
    
    public DoubleProperty() {
        this(null, "", 0.0);
    }

    @Override
    public Class<Double> getTypeClass() {
        return Double.class;
    }

    @Override
    public void set(double newValue) {
        if (boundValue != null) {
            return;
        }
        
        double oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public double get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
