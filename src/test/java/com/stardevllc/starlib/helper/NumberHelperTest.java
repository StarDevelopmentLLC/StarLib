package com.stardevllc.starlib.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberHelperTest {

    @Test
    void romanNumerals() {
        assertEquals("VIII", NumberHelper.romanNumerals(8));
        //TODO Add more stuff here.
    }

    @Test
    void toInt() {
        assertEquals(1, NumberHelper.toInt("1"));
    }

    @Test
    void toFloat() {
        assertEquals(1.0, NumberHelper.toFloat("1.0"));
    }

    @Test
    void toDouble() {
        assertEquals(1.0, NumberHelper.toDouble("1.0"));
    }

    @Test
    void toLong() {
        assertEquals(1L, NumberHelper.toLong("1"));
    }

    @Test
    void toShort() {
        assertEquals((short) 1, NumberHelper.toShort("1"));
    }

    @Test
    void toByte() {
        assertEquals((byte) 1, NumberHelper.toByte("1"));
    }
}