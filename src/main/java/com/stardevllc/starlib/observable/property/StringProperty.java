package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

public class StringProperty extends Property<String> {
    public StringProperty() {
    }

    public StringProperty(String object) {
        super(object);
    }
    
    public String get() {
        return getValue();
    }
}