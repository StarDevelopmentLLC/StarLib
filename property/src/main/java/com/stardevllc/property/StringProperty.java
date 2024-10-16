package com.stardevllc.property;

import com.stardevllc.observable.writable.WritableStringValue;

public class StringProperty extends ObjectProperty<String> implements WritableStringValue {

    public StringProperty(Object bean, String name, String value) {
        super(bean, name, value);
    }

    public StringProperty(String name, String value) {
        super(name, value);
    }

    public StringProperty(String value) {
        super(value);
    }

    public StringProperty() {
    }
}
