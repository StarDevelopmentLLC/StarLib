package com.stardevllc.property;

import com.stardevllc.observable.writable.WritableUUIDValue;

import java.util.UUID;

public class UUIDProperty extends ObjectProperty<UUID> implements WritableUUIDValue {

    public UUIDProperty(Object bean, String name, UUID value) {
        super(bean, name, value);
    }

    public UUIDProperty(String name, UUID value) {
        super(name, value);
    }

    public UUIDProperty(UUID value) {
        super(value);
    }

    public UUIDProperty() {
    }
}
