package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.writable.WritableStringValue;

public class StringProperty extends ObjectProperty<String> implements WritableStringValue {

    public StringProperty(Object bean, String name, String value) {
        super(String.class, bean, name, value);
    }

    public StringProperty(String name, String value) {
        super(String.class, name, value);
    }

    public StringProperty(String value) {
        super(String.class, value);
    }

    public StringProperty() {
        super(String.class);
    }
}
