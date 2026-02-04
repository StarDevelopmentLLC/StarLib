package com.stardevllc.starlib.value;

/**
 * Represents a string that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface StringValue extends ObjectValue<String> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static StringValue of(String v) {
        return () -> v;
    }
    
    /**
     * Gets the value as a safe string
     *
     * @return The safe value
     */
    default String getValueSafe() {
        return get() == null ? "" : get();
    }
}