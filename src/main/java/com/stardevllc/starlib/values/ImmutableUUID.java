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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (value == obj) {
            return true;
        }
        
        if (value != null) {
            return value.equals(obj);
        }
        
        return false;
    }
}