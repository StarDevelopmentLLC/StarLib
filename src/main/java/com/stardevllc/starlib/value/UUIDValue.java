package com.stardevllc.starlib.value;

import java.util.UUID;

/**
 * Represents a uuid that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
public interface UUIDValue extends ObjectValue<UUID> {
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static UUIDValue of(UUID v) {
        return () -> v;
    }
}