package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

public class FloatProperty extends Property<Float> {
    public FloatProperty() {
    }

    public FloatProperty(Float object) {
        super(object);
    }
    
    public float get() {
        return getValue();
    }
}