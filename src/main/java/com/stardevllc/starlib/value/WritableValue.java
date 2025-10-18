package com.stardevllc.starlib.value;

/**
 * An object that wraps a value that can also be changed
 *
 * @param <T> The value type
 */
public interface WritableValue<T> extends Value<T> {
    /**
     * Sets the value
     *
     * @param newValue The new value to set this wrapper to
     */
    void setValue(T newValue);
}