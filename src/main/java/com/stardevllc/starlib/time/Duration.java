package com.stardevllc.starlib.time;

/**
 * Utility class for durations
 */
public class Duration {
    private long time;
    
    /**
     * Constructs empty duration
     */
    public Duration() {
    }
    
    /**
     * Time in milliseconds
     *
     * @return The time
     */
    public long get() {
        return time;
    }
    
    /**
     * Tiem based on unit
     *
     * @param unit The unit
     * @return The converted time
     */
    public double get(TimeUnit unit) {
        return unit.fromMillis(time);
    }
    
    /**
     * Constructs a duration based on a unit and a time value
     *
     * @param unit The unit
     * @param time The time
     */
    public Duration(TimeUnit unit, long time) {
        this.time = unit.toMillis(time);
    }
    
    /**
     * Adds time to the duration
     *
     * @param unit The unit
     * @param time The time
     * @return This duration
     */
    public Duration add(TimeUnit unit, long time) {
        this.time += unit.toMillis(time);
        return this;
    }
    
    /**
     * Subtracts time from this duratioin
     *
     * @param unit The unit
     * @param time The time
     * @return This duration
     */
    public Duration subtract(TimeUnit unit, long time) {
        this.time -= unit.toMillis(time);
        return this;
    }
    
    /**
     * Adds the provided duration to this one
     *
     * @param duration The other duration
     * @return This duration
     */
    public Duration add(Duration duration) {
        this.time += duration.get();
        return this;
    }
    
    /**
     * Subtracts the provided duration from this one
     *
     * @param duration The other duration
     * @return This duration
     */
    public Duration subtract(Duration duration) {
        this.time -= duration.get();
        return this;
    }
    
    /**
     * Makes the time the absolute value (removes the - sign)
     *
     * @return This duration
     */
    public Duration abs() {
        this.time = Math.abs(this.time);
        return this;
    }
}