package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteStringConverterTest {

    static ByteStringConverter converter = new ByteStringConverter();
    
    @Test
    void fromString() {
        assertEquals((byte) 3, converter.fromString("3"));
    }

    @Test
    void testToString() {
        assertEquals("3", converter.toString((byte) 3));
    }
}