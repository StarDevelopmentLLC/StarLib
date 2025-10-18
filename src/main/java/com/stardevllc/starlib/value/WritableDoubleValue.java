package com.stardevllc.starlib.value;

/**
 * Represents a Writable Double Observable value
 */
public interface WritableDoubleValue extends DoubleValue, WritableNumberValue<Double> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(double value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Double value) {
        set(value);
    }
}