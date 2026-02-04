package com.stardevllc.starlib.value;

/**
 * Represents a byte that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ByteValue extends NumberValue<Byte> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static ByteValue of(byte v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    byte get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Byte getValue() {
        return get();
    }
}