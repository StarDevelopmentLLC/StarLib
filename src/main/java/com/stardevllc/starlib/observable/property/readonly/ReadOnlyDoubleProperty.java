package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableDoubleValue;

public class ReadOnlyDoubleProperty extends AbstractReadOnlyProperty<Double> implements ObservableDoubleValue {
    
    protected double value;
    
    public ReadOnlyDoubleProperty(Object bean, String name, double value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyDoubleProperty(Object bean, String name) {
        super(bean, name, Double.class);
    }
    
    public ReadOnlyDoubleProperty(double value) {
        this(null, null, value);
    }
    
    public ReadOnlyDoubleProperty() {
        this(null, null);
    }
    
    @Override
    public double get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
