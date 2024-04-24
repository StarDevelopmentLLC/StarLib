package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStringConverterTest {

    static CharacterStringConverter converter = new CharacterStringConverter();
    
    @Test
    void fromString() {
        assertEquals('A', converter.fromString("A"));
    }

    @Test
    void testToString() {
        assertEquals("A", converter.toString('A'));
    }
}