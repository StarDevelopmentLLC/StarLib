package com.stardevllc.starlib.numbers;

public final class SimpleCalculator {
    public static Number negate(Number number) {
        return switch (number) {
            case Double ignored -> -number.doubleValue();
            case Float ignored -> -number.floatValue();
            case Long ignored -> -number.longValue();
            case Integer ignored -> -number.intValue();
            case Short ignored -> -number.shortValue();
            case Byte ignored -> -number.byteValue();
            case null, default -> 0;
        };

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

        try {
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
        } catch (ArithmeticException e) {
            return 0;
        }
    }
}
