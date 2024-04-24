package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongStringConverterTest {
    
    static LongStringConverter converter = new LongStringConverter();
    
    @Test
    void fromString() {
        assertEquals(123L, converter.fromString("123"));
    }

    @Test
    void testToString() {
        assertEquals("123", converter.toString(123L));
    }
}