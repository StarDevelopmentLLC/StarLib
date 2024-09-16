package com.stardevllc.starlib.numbers;

public final class Operations {
    public static Number negate(Number number) {
        if (number == null) {
            return 0;
        }
        
        if (number instanceof Double) {
            return -number.doubleValue();
        } else if (number instanceof Float) {
            return -number.floatValue();
        } else if (number instanceof Long) {
            return -number.longValue();
        } else if (number instanceof Integer) {
            return -number.intValue();
        } else if (number instanceof Short) {
            return -number.shortValue();
        } else if (number instanceof Byte) {
            return -number.byteValue();
        }

        return 0;
    }
    
    public static Number add(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        if (left instanceof Double || right instanceof Double) {
            return left.doubleValue() + right.doubleValue();
        } else if (left instanceof Float || right instanceof Float) {
            return left.floatValue() + right.floatValue();
        } else if (left instanceof Long || right instanceof Long) {
            return left.longValue() + right.longValue();
        } else if (left instanceof Integer || right instanceof Integer) {
            return left.intValue() + right.intValue();
        } else if (left instanceof Short || right instanceof Short) {
            return left.shortValue() + right.shortValue();
        } else if (left instanceof Byte || right instanceof Byte) {
            return left.byteValue() + right.byteValue();
        }
        
        return 0;
    }
    
    public static Number subtract(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }

        if (left instanceof Double || right instanceof Double) {
            return left.doubleValue() - right.doubleValue();
        } else if (left instanceof Float || right instanceof Float) {
            return left.floatValue() - right.floatValue();
        } else if (left instanceof Long || right instanceof Long) {
            return left.longValue() - right.longValue();
        } else if (left instanceof Integer || right instanceof Integer) {
            return left.intValue() - right.intValue();
        } else if (left instanceof Short || right instanceof Short) {
            return left.shortValue() - right.shortValue();
        } else if (left instanceof Byte || right instanceof Byte) {
            return left.byteValue() - right.byteValue();
        }
        
        return 0;
    }
    
    public static Number multiply(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }

        if (left instanceof Double || right instanceof Double) {
            return left.doubleValue() * right.doubleValue();
        } else if (left instanceof Float || right instanceof Float) {
            return left.floatValue() * right.floatValue();
        } else if (left instanceof Long || right instanceof Long) {
            return left.longValue() * right.longValue();
        } else if (left instanceof Integer || right instanceof Integer) {
            return left.intValue() * right.intValue();
        } else if (left instanceof Short || right instanceof Short) {
            return left.shortValue() * right.shortValue();
        } else if (left instanceof Byte || right instanceof Byte) {
            return left.byteValue() * right.byteValue();
        }
        
        return 0;
    }
    
    public static Number divide(Number left, Number right) {
        if (left == null || right == null) {
            return 0;
        }
        
        if (right.doubleValue() == 0.0) {
            return 0;
        }

        if (left instanceof Double || right instanceof Double) {
            return left.doubleValue() / right.doubleValue();
        } else if (left instanceof Float || right instanceof Float) {
            return left.floatValue() / right.floatValue();
        } else if (left instanceof Long || right instanceof Long) {
            return left.longValue() / right.longValue();
        } else if (left instanceof Integer || right instanceof Integer) {
            return left.intValue() / right.intValue();
        } else if (left instanceof Short || right instanceof Short) {
            return left.shortValue() / right.shortValue();
        } else if (left instanceof Byte || right instanceof Byte) {
            return left.byteValue() / right.byteValue();
        }
        
        return left.doubleValue() / right.doubleValue();
    }
}
