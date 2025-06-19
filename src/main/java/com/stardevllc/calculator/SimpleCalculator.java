package com.stardevllc.calculator;

/**
 * <pre>A class that allows you to perform arithmentic and mathmatical operations on {@link Number}s
 * Note: This does not support {@link java.math.BigInteger} or {@link java.math.BigDecimal} yet</pre>
 */
public final class SimpleCalculator {
    public static Number min(Number left, Number right) {
        if (left == null && right == null) {
            return 0;
        } else if (left == null) { //Right is not null here
            return right;
        } else if (right == null) { //Left is not null here
            return left;
        }
        
        if (left instanceof Double || right instanceof Double) {
            return Math.min(left.doubleValue(), right.doubleValue());
        } else if (left instanceof Float || right instanceof Float) {
            return Math.min(left.floatValue(), right.floatValue());
        } else if (left instanceof Long || right instanceof Long) {
            return Math.min(left.longValue(), right.longValue());
        } else if (left instanceof Integer || right instanceof Integer) {
            return Math.min(left.intValue(), right.intValue());
        } else if (left instanceof Short || right instanceof Short) {
            return Math.min(left.shortValue(), right.shortValue());
        } else if (left instanceof Byte || right instanceof Byte) {
            return Math.min(left.byteValue(), right.byteValue());
        }
        
        return 0;
    }
    
    public static Number max(Number left, Number right) {
        if (left == null && right == null) {
            return 0;
        } else if (left == null) { //Right is not null here
            return right;
        } else if (right == null) { //Left is not null here
            return left;
        }
        
        if (left instanceof Double || right instanceof Double) {
            return Math.max(left.doubleValue(), right.doubleValue());
        } else if (left instanceof Float || right instanceof Float) {
            return Math.max(left.floatValue(), right.floatValue());
        } else if (left instanceof Long || right instanceof Long) {
            return Math.max(left.longValue(), right.longValue());
        } else if (left instanceof Integer || right instanceof Integer) {
            return Math.max(left.intValue(), right.intValue());
        } else if (left instanceof Short || right instanceof Short) {
            return Math.max(left.shortValue(), right.shortValue());
        } else if (left instanceof Byte || right instanceof Byte) {
            return Math.max(left.byteValue(), right.byteValue());
        }
        
        return 0;
    }
    
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
        if (left == null && right == null) {
            return 0;
        } else if (left == null) { //Right is not null here
            return right;
        } else if (right == null) { //Left is not null here
            return left;
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
        if (left == null && right == null) {
            return 0;
        } else if (left == null) { //Right is not null here
            return negate(right); //This is just like 0 - right
        } else if (right == null) { //Left is not null here
            return left; //its a no-op if right is null, its just lilke left - 0
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
            return 0; //If either side is null, its just like multiplying by 0, which by definition is zero
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
            return 0; //If any side is null, just return 0, this will not trigger a divide by zero in that case as its undefined, but whatever
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
