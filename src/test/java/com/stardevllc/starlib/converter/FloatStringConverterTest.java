package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloatStringConverterTest {
    
    static FloatStringConverter converter = new FloatStringConverter();
    
    @Test
    void fromString() {
        assertEquals(1.1F, converter.fromString("1.1"));
    }

    @Test
    void testToString() {
        assertEquals("1.1", converter.toString(1.1F));
    }
}