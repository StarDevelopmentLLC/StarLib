package com.stardevllc.starlib.value;

/**
 * Represents a character that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface CharacterValue extends Value<Character> {
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