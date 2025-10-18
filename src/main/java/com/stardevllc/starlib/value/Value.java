package com.stardevllc.starlib.value;

/**
 * An object that holds a value
 *
 * @param <T> The value type
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface Value<T> {
    /**
     * Gets the value that this object wraps
     *
     * @return The value
     */
    T getValue();
}
