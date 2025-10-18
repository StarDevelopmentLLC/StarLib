package com.stardevllc.starlib.value;

/**
 * Represents a Writable Long Observable value
 */
public interface WritableLongValue extends LongValue, WritableNumberValue<Long> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(long value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Long value) {
        set(value);
    }
}