package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerStringConverterTest {
    
    static IntegerStringConverter converter = new IntegerStringConverter();
    
    @Test
    void fromString() {
        assertEquals(123, converter.fromString("123"));
    }

    @Test
    void testToString() {
        assertEquals("123", converter.toString(123));
    }
}