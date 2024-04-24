package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleStringConverterTest {

    static DoubleStringConverter converter = new DoubleStringConverter();
    
    @Test
    void fromString() {
        assertEquals(1.1, converter.fromString("1.1"));
    }

    @Test
    void testToString() {
        assertEquals("1.1", converter.toString(1.1));
    }
}