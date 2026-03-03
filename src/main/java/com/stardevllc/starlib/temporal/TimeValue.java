package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.serialization.StarSerializable;
import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

/**
 * This class represents a time value and has all of the math associated with it. 
 */
public final class TimeValue implements Comparable<TimeValue>, Cloneable, StarSerializable {
    
    private long time;

    public TimeValue() {}

    public TimeValue(long time) {
        this.time = time;
    }
    
    public TimeValue(Map<String, Object> serialized) {
        this.time = (long) serialized.get("time");
    }
    
    public void divide(long divisor) {
        this.time /= divisor;
    }
    
    public void divide(TimeValue divisor) {
        this.time /= divisor.time;
    }
    
    public void multiply(double factor) {
        this.time *= (long) factor;
    }
    
    public long getTime() {
        return time;
    }

    public boolean equals(Object other) {
        if (other instanceof TimeValue instant) {
            return this.time == instant.time;
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.time);
    }
    
    @Override
    public int compareTo(TimeValue other) {
        if (other == null) {
            return 1;
        }
        
        return Long.compare(this.time, other.time);
    }
    
    @Override
    public TimeValue clone() {
        return new TimeValue(this.time);
    }

    public void add(TimeValue other) {
        this.add(other.time);
    }
    
    public void subtract(TimeValue other) {
        this.subtract(other.time);
    }
    
    public void add(TimeUnit unit, long amount) {
        this.add(unit.toMillis(amount));
    }
    
    public void subtract(TimeUnit unit, long amount) {
        this.subtract(unit.toMillis(amount));
    }
    
    public void add(long milliseconds) {
        this.time += milliseconds;
    }
    
    public void subtract(long milliseconds) {
        this.time -= milliseconds;
    }
    
    public boolean lessThan(TimeValue other) {
        if (other == null) {
            return false;
        }
        
        return this.time < other.time;
    }
    
    public boolean lessThanOrEqualTo(TimeValue other) {
        if (other == null) {
            return false;
        }
        
        if (equals(other)) {
            return true;
        }

        return lessThan(other);
    }

    public boolean greaterThan(TimeValue other) {
        if (other == null) {
            return false;
        }

        return this.time > other.time;
    }
    
    public boolean greaterThanOrEqualTo(TimeValue other) {
        if (other == null) {
            return false;
        }

        if (equals(other)) {
            return true;
        }

        return greaterThan(other);
    }
    
    @Override
    public String toString() {
        return "TimeValue{" +
                "time=" + time +
                '}';
    }
    
    @Override
    public Map<String, Object> serialize() {
        return Map.of("time", this.time);
    }
    
    public void set(long time) {
        this.time = time;
    }
}
