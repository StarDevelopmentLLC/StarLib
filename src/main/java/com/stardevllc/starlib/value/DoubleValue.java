package com.stardevllc.starlib.value;

/**
 * Represents a double that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
public interface DoubleValue extends NumberValue<Double> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static DoubleValue of(double v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    double get();
    
    /**
     * {@inheritDoc}
     */
    default Double getValue() {
        return get();
    }
}