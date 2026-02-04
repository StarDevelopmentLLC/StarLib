package com.stardevllc.starlib.value;

/**
 * Represents an Object that can be observed
 *
 * @param <T> The object type
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObjectValue<T> extends Value<T> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static <T> ObjectValue<T> of(T v) {
        return () -> v;
    }
    
    /**
     * Gets this value. Mainly for consistency with the others
     *
     * @return The value
     */
    T get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default T getValue() {
        return get();
    }
}