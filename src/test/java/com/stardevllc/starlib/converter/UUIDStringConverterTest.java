package com.stardevllc.starlib.converter;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDStringConverterTest {

    UUIDStringConverter converter = new UUIDStringConverter();
    
    @Test
    void testToString() {
        assertEquals("3f7891ce-5a73-4d52-a2ba-299839053fdc", converter.toString(UUID.fromString("3f7891ce-5a73-4d52-a2ba-299839053fdc")));
    }

    @Test
    void fromString() {
        assertNotNull(converter.fromString("3f7891ce-5a73-4d52-a2ba-299839053fdc"));
    }
}