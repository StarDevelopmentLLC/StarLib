package com.stardevllc.starlib.value;

/**
 * Represents a string that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface StringValue extends ObjectValue<String> {
    /**
     * Gets the value as a safe string
     *
     * @return The safe value
     */
    default String getValueSafe() {
        return get() == null ? "" : get();
    }
}