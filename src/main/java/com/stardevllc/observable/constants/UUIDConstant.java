package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableUUIDValue;

import java.util.UUID;

/**
 * Represents a constant Boolean value
 */
public class UUIDConstant extends ObjectConstant<UUID> implements ObservableUUIDValue {
    /**
     * Constructs a new Boolean constant with the provided value
     *
     * @param object The value to set the constant to
     */
    public UUIDConstant(UUID object) {
        super(object);
    }
}
