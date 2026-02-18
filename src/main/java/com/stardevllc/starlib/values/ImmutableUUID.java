package com.stardevllc.starlib.values;

import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public final class ImmutableUUID implements Value<UUID> {
    
    public static ImmutableUUID of(UUID value) {
        return new ImmutableUUID(value);
    }
    
    private final UUID value;
    
    public ImmutableUUID(UUID value) {
        this.value = value;
    }
    
    public UUID get() {
        return value;
    }
    
    @Override
    public UUID getValue() {
        return get();
    }
}