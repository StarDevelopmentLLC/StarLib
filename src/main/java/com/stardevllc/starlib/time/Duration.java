package com.stardevllc.starlib.time;

/**
 * This class will allow representing a duration that will allow arithmetic operations using the custom TimeUnits
 */
public class Duration {
    private long time;
    
    public Duration() {}
    
    public Duration(TimeUnit unit, long time) {
        this.time = unit.toMillis(time);
    }
    
    public Duration add(TimeUnit unit, long time) {
        this.time += unit.toMillis(time);
        return this;
    }
    
    public Duration subtract(TimeUnit unit, long time) {
        this.time -= unit.toMillis(time);
        return this;
    }
    
    public Duration multiply(long factor) {
        this.time *= factor;
        return this;
    }
    
    public Duration divide(long divisor) {
        this.time /= divisor;
        return this;
    }
    
    public Duration divide(double divisor) {
        this.time /= (long) divisor;
        return this;
    }
    
    public Duration pow(long exponent) {
        this.time = (long) Math.pow(this.time, exponent);
        return this;
    }
    
    public Duration square() {
        return pow(2);
    }
    
    public Duration squrt() {
        this.time = (long) Math.sqrt(this.time);
        return this;
    }
    
    public Duration abs() {
        this.time = Math.abs(this.time);
        return this;
    }
}