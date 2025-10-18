package com.stardevllc.starlib.value;

/**
 * Represents a float that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface FloatValue extends NumberValue<Float> {
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