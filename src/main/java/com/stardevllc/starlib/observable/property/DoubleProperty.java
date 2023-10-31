package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

public class DoubleProperty extends Property<Double> {
    public DoubleProperty() {
    }

    public DoubleProperty(Double object) {
        super(object);
    }
    
    public double get() {
        return getValue();
    }
}