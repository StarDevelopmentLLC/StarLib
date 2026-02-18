package com.stardevllc.starlib.value;

/**
 * Represents an integer that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
public interface IntegerValue extends NumberValue<Integer> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static IntegerValue of(int v) {
        return () -> v;
    }
    
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