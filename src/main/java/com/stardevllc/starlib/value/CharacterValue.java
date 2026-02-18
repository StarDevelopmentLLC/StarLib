package com.stardevllc.starlib.value;

/**
 * Represents a character that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Deprecated(since = "0.24.0")
public interface CharacterValue extends Value<Character> {
    
    /**
     * Creates a constant value using the parameter
     *
     * @param v The value
     * @return The value
     */
    static CharacterValue of(char v) {
        return () -> v;
    }
    
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    char get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Character getValue() {
        return get();
    }
}