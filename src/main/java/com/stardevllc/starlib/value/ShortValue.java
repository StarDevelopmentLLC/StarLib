package com.stardevllc.starlib.value;

/**
 * Represents a short that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ShortValue extends NumberValue<Short> {
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