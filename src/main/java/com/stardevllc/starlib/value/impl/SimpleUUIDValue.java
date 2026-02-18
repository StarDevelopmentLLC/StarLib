package com.stardevllc.starlib.value.impl;

import com.stardevllc.starlib.value.WritableUUIDValue;

import java.util.UUID;

@Deprecated(since = "0.24.0")
public class SimpleUUIDValue extends SimpleObjectValue<UUID> implements WritableUUIDValue {
    public SimpleUUIDValue() {
    }
    
    public SimpleUUIDValue(UUID value) {
        super(value);
    }
}
