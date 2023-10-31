package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

public class IntegerProperty extends Property<Integer> {
    public IntegerProperty() {
    }

    public IntegerProperty(Integer object) {
        super(object);
    }
    
    public int get() {
        return getValue();
    }
}