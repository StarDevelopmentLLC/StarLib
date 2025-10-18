package com.stardevllc.starlib.value;

/**
 * Represents a long that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface LongValue extends NumberValue<Long> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    long get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Long getValue() {
        return get();
    }
}