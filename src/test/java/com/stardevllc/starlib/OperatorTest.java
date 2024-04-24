package com.stardevllc.starlib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {
    @Test
    void add() {
        assertEquals(2, Operator.ADD.calculate(1, 1));
    }
    
    @Test
    void subtract() {
        assertEquals(0, Operator.SUBTRACT.calculate(1, 1));
    }
    
    @Test
    void multiply() {
        assertEquals(4, Operator.MULTIPLY.calculate(2, 2));
    }
    
    @Test
    void divide() {
        assertEquals(2, Operator.DIVIDE.calculate(4, 2));
    }
}