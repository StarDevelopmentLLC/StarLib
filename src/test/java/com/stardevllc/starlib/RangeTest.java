package com.stardevllc.starlib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {
    
    static Range<String> range = new Range<>(0, 10, "Hello");
    
    @Test
    void inRange() {
        assertTrue(range.contains(3));
    }
    
    @Test
    void outRange() {
        assertFalse(range.contains(11));
    }
}