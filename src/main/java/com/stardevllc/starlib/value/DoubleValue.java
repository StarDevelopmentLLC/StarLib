package com.stardevllc.starlib.value;

/**
 * Represents a double that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface DoubleValue extends NumberValue<Double> {
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