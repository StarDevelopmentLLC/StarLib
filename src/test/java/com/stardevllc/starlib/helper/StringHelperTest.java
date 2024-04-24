package com.stardevllc.starlib.helper;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {

    @Test
    void titlize() {
        assertEquals("Hello World", StringHelper.titlize("hello_world"));
    }

    @Test
    void join() {
        Collection<String> col = List.of("1", "2", "3");
        assertEquals("1, 2, 3", StringHelper.join(col, ", "));
    }

    @Test
    void toUUID() {
        assertNotNull(StringHelper.toUUID("3f7891ce5a734d52a2ba299839053fdc"));
    }

    @Test
    void getStringSafe() {
        assertNotNull(StringHelper.getStringSafe(null));
    }

    @Test
    void isEmpty() {
        assertTrue(StringHelper.isEmpty(""));
        assertTrue(StringHelper.isEmpty(null));
        assertFalse(StringHelper.isEmpty("a"));
    }
}