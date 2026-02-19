package com.stardevllc.starlib.values;

/**
 * An object that holds a value
 *
 * @param <T> The value type
 */
@FunctionalInterface
public interface Value<T> {
    /**
     * Gets the value that this object wraps
     *
     * @return The value
     */
    T getValue();
    
    String toString();
    
    boolean equals(Object o);
    
    int hashCode();
}
