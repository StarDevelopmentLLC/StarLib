package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.value.ObservableStringValue;

public class ReadOnlyStringProperty extends ReadOnlyObjectProperty<String> implements ObservableStringValue {
    public ReadOnlyStringProperty(Object bean, String name, String value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyStringProperty(Object bean, String name) {
        super(bean, name, String.class);
    }
    
    public ReadOnlyStringProperty(String value) {
        this(null, null, value);
    }
    
    public ReadOnlyStringProperty() {
        this(null, null);
    }
}
