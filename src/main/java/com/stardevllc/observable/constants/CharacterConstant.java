package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableCharacterValue;

/**
 * Represents a constant Character value
 */
public class CharacterConstant implements ObservableCharacterValue {
    
    private final char value;
    
    /**
     * Constructs a new Character constant with the provided value
     *
     * @param value The value to set the constant to
     */
    public CharacterConstant(char value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public char get() {
        return value;
    }
}
