package com.stardevllc.starlib.value;

/**
 * Represents a byte that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
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