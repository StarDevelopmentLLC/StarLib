package com.stardevllc.starlib.value;

/**
 * Represents a byte that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ByteValue extends NumberValue<Byte> {
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