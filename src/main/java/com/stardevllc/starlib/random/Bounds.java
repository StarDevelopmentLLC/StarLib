package com.stardevllc.starlib.random;

import java.util.Random;

public record Bounds(long min, long max, long divisor) {
    private static final Random RANDOM = new Random();
    
    //This makes a bounds have a single value
    public static Bounds fixed(long value) {
        return new Bounds(value, value);
    }
    
    public static Bounds range(long min, long max) {
        return new Bounds(min, max);
    }
    
    public static Bounds range(long min, long max, long divisor) {
        return new Bounds(min, max, divisor);
    }
    
    public Bounds(long min, long max) {
        this(min, max, 1);
    }
    
    public long generate() {
        //Shortcut check to prevent calling a random method if they are the same
        if (min == max) {
            return min;
        }
        return RANDOM.nextLong(min, max + 1);
    }
    
    public double generateAndDivide() {
        return generate() / (1.0 * divisor);
    }
    
    public Bounds clone() {
        return new Bounds(this.min, this.max, this.divisor);
    }
}