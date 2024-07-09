package com.stardevllc.starlib.time;

public class Duration {
    private long time;
    
    public Duration() {}
    
    public long getTime() {
        return time;
    }
    
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
    
    public Duration abs() {
        this.time = Math.abs(this.time);
        return this;
    }
}