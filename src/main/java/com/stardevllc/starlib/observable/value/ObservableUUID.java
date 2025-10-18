package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.value.UUIDValue;

import java.util.UUID;

/**
 * Represents a uuid that can be observed
 */
public interface ObservableUUID extends ObservableObject<UUID>, UUIDValue {
    
}