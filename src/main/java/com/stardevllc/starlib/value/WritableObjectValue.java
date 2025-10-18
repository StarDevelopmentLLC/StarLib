package com.stardevllc.starlib.value;

/**
 * Represents a Writable Object Observable value
 * 
 * @param <T> The Object type
 */
public interface WritableObjectValue<T> extends ObjectValue<T>, WritableValue<T> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(T value);
    
    @Override
    default void setValue(T value) {
        set(value);
    }
}