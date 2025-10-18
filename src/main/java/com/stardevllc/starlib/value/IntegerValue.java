package com.stardevllc.starlib.value;

/**
 * Represents an integer that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface IntegerValue extends NumberValue<Integer> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    int get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Integer getValue() {
        return get();
    }
}