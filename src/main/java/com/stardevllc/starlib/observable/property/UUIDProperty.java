package com.stardevllc.starlib.observable.property;

import com.stardevllc.starlib.observable.Property;

import java.util.UUID;

public class UUIDProperty extends Property<UUID> {
    public UUIDProperty() {
    }

    public UUIDProperty(UUID object) {
        super(object);
    }
    
    public UUID get() {
        return getValue();
    }
}