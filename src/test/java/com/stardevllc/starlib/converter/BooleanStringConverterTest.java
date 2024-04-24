package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanStringConverterTest {

    static BooleanStringConverter converter = new BooleanStringConverter();
    
    @Test
    void fromString() {
        assertEquals(true, converter.fromString("true"));
        assertEquals(false, converter.fromString("false"));
    }

    @Test
    void testToString() {
        assertEquals("true", converter.toString(true));
        assertEquals("false", converter.toString(false));
    }
}