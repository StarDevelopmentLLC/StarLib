package com.stardevllc.starlib.helper;

import java.util.Random;

/**
 * Number based utilities
 */
public final class NumberHelper {
    private NumberHelper() {
    }
    
    /**
     * Suffix used in writing numbers in sentances
     *
     * @param number The number
     * @return The number + suffix
     */
    public static String getNumberSuffix(long number) {
        String numberString = String.valueOf(number);
        
        if (numberString.charAt(numberString.length() - 1) == '1' && number != 11) {
            return number + "st";
        } else if (numberString.charAt(numberString.length() - 1) == '2' && number != 12) {
            return number + "nd";
        } else if (numberString.charAt(numberString.length() - 1) == '3' && number != 13) {
            return number + "rd";
        } else {
            return number + "th";
        }
    }
    
    /**
     * Generate random number in range
     *
     * @param min The min
     * @param max The max
     * @return The number
     */
    public static int randomInRange(int min, int max) {
        return randomInRange(new Random(), min, max);
    }
    
    /**
     * Generate random number in range using provided random
     *
     * @param random The random instnace
     * @param min    The min
     * @param max    The max
     * @return The number
     */
    public static int randomInRange(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }
    
    /**
     * Converts to an int
     *
     * @param object The object
     * @return The converted value
     */
    public static int toInt(Object object) {
        if (object instanceof Number number) {
            return number.intValue();
        }
        
        try {
            return Integer.parseInt(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
            try {
                return (int) Double.parseDouble(object.toString());
            } catch (NumberFormatException | NullPointerException ex) {}
        }
        return 0;
    }
    
    /**
     * Converts to a float
     *
     * @param object The object
     * @return The converted value
     */
    public static float toFloat(Object object) {
        if (object instanceof Number number) {
            return number.floatValue();
        }
        
        try {
            return Float.parseFloat(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }
    
    /**
     * Converts to a double
     *
     * @param object The object
     * @return The converted value
     */
    public static double toDouble(Object object) {
        if (object instanceof Number number) {
            return number.doubleValue();
        }
        
        try {
            return Double.parseDouble(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
        }
        return 0;
    }
    
    /**
     * Converts to a long
     *
     * @param object The object
     * @return The converted value
     */
    public static long toLong(Object object) {
        if (object instanceof Number number) {
            return number.longValue();
        }
        
        try {
            return Long.parseLong(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
            try {
                return (long) Double.parseDouble(object.toString());
            } catch (NumberFormatException | NullPointerException ex) {}
        }
        return 0;
    }
    
    /**
     * Converts to a short
     *
     * @param object The object
     * @return The converted value
     */
    public static short toShort(Object object) {
        if (object instanceof Number number) {
            return number.shortValue();
        }
        
        try {
            return Short.parseShort(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
            try {
                return (short) Double.parseDouble(object.toString());
            } catch (NumberFormatException | NullPointerException ex) {}
        }
        return 0;
    }
    
    /**
     * Converts to a byte
     *
     * @param object The object
     * @return The converted value
     */
    public static byte toByte(Object object) {
        if (object instanceof Number number) {
            return number.byteValue();
        }
        
        try {
            return Byte.parseByte(object.toString());
        } catch (NumberFormatException | NullPointerException e) {
            try {
                return (byte) Double.parseDouble(object.toString());
            } catch (NumberFormatException | NullPointerException ex) {}
        }
        return 0;
    }
    
    /**
     * Finds the min value using the Number class as parameters
     *
     * @param left  The left value
     * @param right The right value
     * @return The min value
     */
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
    
    /**
     * Finds the max value using the Number class as parameters
     *
     * @param left  The left value
     * @param right The right value
     * @return The max value
     */
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
    
    /**
     * Negates the number using the Number class as input
     *
     * @param number The number
     * @return The negated number
     */
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
    
    /**
     * Adds two numbers together using the Number class as input
     *
     * @param left  The left
     * @param right The right
     * @return The result
     */
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
    
    /**
     * Subtracts two numbers using the Number class as input
     *
     * @param left  The left
     * @param right The right
     * @return The result
     */
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
    
    /**
     * Multiplies two numbers together using the Number class as input
     *
     * @param left  The left
     * @param right The right
     * @return The result
     */
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
    
    /**
     * Divides two numbers using the Number class as input
     *
     * @param left  The left
     * @param right The right
     * @return The result
     */
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