package com.stardevllc.starlib.values;

import java.util.UUID;

public class MutableUUID implements MutableValue<UUID> {
    
    public static MutableUUID of() {
        return new MutableUUID();
    }
    
    public static MutableUUID of(UUID value) {
        return new MutableUUID(value);
    }
    
    protected UUID value;
    
    public MutableUUID() {}
    
    public MutableUUID(UUID value) {
        this.value = value;
    }
    
    public void set(UUID value) {
        this.value = value;
    }
    
    @Override
    public void setValue(UUID value) {
        set(value);
    }
    
    public UUID get() {
        return value;
    }
    
    @Override
    public UUID getValue() {
        return get();
    }
}
