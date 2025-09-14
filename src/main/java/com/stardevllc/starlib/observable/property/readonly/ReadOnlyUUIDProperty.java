package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.value.ObservableUUIDValue;

import java.util.UUID;

public class ReadOnlyUUIDProperty extends ReadOnlyObjectProperty<UUID> implements ObservableUUIDValue {
    public ReadOnlyUUIDProperty(Object bean, String name, UUID value) {
        this(bean, name);
        this.value = value;
    }
    
    public ReadOnlyUUIDProperty(Object bean, String name) {
        super(bean, name, UUID.class);
    }
    
    public ReadOnlyUUIDProperty(UUID value) {
        this(null, null, value);
    }
    
    public ReadOnlyUUIDProperty() {
        this(null, null);
    }
}
