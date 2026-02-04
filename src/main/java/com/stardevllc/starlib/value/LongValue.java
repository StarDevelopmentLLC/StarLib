package com.stardevllc.starlib.value;

/**
 * Represents a long that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface LongValue extends NumberValue<Long> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static LongValue of(long v) {
        return () -> v;
    }
    
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