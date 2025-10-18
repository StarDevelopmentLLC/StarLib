package com.stardevllc.starlib.value;

/**
 * Represents a Writable Float Observable value
 */
public interface WritableFloatValue extends FloatValue, WritableNumberValue<Float> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(float value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Float value) {
        set(value);
    }
}