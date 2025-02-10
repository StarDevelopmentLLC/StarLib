package com.stardevllc.property;

import com.stardevllc.observable.writable.WritableUUIDValue;

import java.util.UUID;

public class UUIDProperty extends ObjectProperty<UUID> implements WritableUUIDValue {

    public UUIDProperty(Object bean, String name, UUID value) {
        super(UUID.class, bean, name, value);
    }

    public UUIDProperty(String name, UUID value) {
        super(UUID.class, name, value);
    }

    public UUIDProperty(UUID value) {
        super(UUID.class, value);
    }

    public UUIDProperty() {
        super(UUID.class);
    }
}
