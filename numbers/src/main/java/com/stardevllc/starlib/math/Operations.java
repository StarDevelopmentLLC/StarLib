package com.stardevllc.starlib.math;

public final class Operations {
    public static Number negate(Number number) {
        if (number == null) {
            return 0;
        }

        return -number.doubleValue();
    }
    
    public static Number add(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        return left.doubleValue() + right.doubleValue();
    }
    
    public static Number subtract(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        return left.doubleValue() - right.doubleValue();
    }
    
    public static Number multiply(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        return left.doubleValue() * right.doubleValue();
    }
    
    public static Number divide(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        if (right.doubleValue() == 0.0d) {
            return 0;
        }
        
        return left.doubleValue() / right.doubleValue();
    }
}
