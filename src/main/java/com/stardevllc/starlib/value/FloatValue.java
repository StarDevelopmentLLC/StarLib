package com.stardevllc.starlib.value;

/**
 * Represents a float that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface FloatValue extends NumberValue<Float> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static FloatValue of(float v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    float get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Float getValue() {
        return get();
    }
}