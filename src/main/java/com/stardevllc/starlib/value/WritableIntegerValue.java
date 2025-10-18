package com.stardevllc.starlib.value;

/**
 * Represents a Writable Integer Observable value
 */
public interface WritableIntegerValue extends IntegerValue, WritableNumberValue<Integer> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(int value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Integer value) {
        set(value);
    }
}