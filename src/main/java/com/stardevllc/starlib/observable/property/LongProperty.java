package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

public class LongProperty extends Property<Long> {
    public LongProperty() {
    }

    public LongProperty(Long object) {
        super(object);
    }
    
    public long get() {
        return getValue();
    }
}