package com.stardevllc.starlib;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {
    
    static Range<String> helloRange = new Range<>(0, 10, "Hello");
    static Range<String> worldRange = new Range<>(11, 20, "World");
    
    static RangeSet<String> rangeSet = new RangeSet<>();
    
    @BeforeAll
    static void setup() {
        rangeSet.add(helloRange);
        rangeSet.add(worldRange);
    }
    
    @Test
    void singleInRange() {
        assertTrue(helloRange.contains(3));
    }
    
    @Test
    void singleOutRange() {
        assertFalse(helloRange.contains(11));
    }
    
    @Test
    void multiInRange() {
        assertEquals("Hello", rangeSet.get(3));
        assertEquals("World", rangeSet.get(13));
    }
}