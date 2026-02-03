package com.stardevllc.starlib.value;

/**
 * Represents a Writable Short Observable value
 */
public interface WritableByteValue extends ByteValue, WritableNumberValue<Byte> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(byte value);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Byte value) {
        set(value);
    }
}