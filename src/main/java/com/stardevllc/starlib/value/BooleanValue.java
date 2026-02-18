package com.stardevllc.starlib.value;

/**
 * Represents a Boolean Value that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
public interface BooleanValue extends Value<Boolean> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The boolean value
     */
    static BooleanValue of(boolean v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    boolean get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Boolean getValue() {
        return get();
    }
}