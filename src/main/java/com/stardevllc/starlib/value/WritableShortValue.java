package com.stardevllc.starlib.value;

/**
 * Represents a Writable Short Observable value
 */
public interface WritableShortValue extends ShortValue, WritableNumberValue<Short> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(short value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Short value) {
        set(value);
    }
}