package com.stardevllc.starlib.value;

/**
 * Represents a Writable Boolean Observable value
 */
public interface WritableBooleanValue extends BooleanValue, WritableValue<Boolean> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(boolean value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Boolean value) {
        set(value);
    }
}