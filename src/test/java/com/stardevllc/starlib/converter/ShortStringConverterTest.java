package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortStringConverterTest {
    
    static ShortStringConverter converter = new ShortStringConverter();
    
    @Test
    void fromString() {
        assertEquals((short) 123, converter.fromString("123"));
    }

    @Test
    void testToString() {
        assertEquals("123", converter.toString((short) 123));
    }
}