package com.stardevllc.starlib.value;

/**
 * Represents a Boolean Value that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface BooleanValue extends Value<Boolean> {
    
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