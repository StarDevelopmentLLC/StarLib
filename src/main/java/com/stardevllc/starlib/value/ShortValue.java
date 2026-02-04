package com.stardevllc.starlib.value;

/**
 * Represents a short that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ShortValue extends NumberValue<Short> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static ShortValue of(short v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    short get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Short getValue() {
        return get();
    }
}