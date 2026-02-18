package com.stardevllc.starlib.value;

/**
 * Represents a Writable Character Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableCharacterValue extends CharacterValue, WritableValue<Character> {
    /**
     * Sets the value to the provided value
     *
     * @param newValue The new value
     */
    void set(char newValue);
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Character character) {
        set(character);
    }
}