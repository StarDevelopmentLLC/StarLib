package com.stardevllc.starlib.temporal;

public class DurationDate implements Cloneable {
    protected final Duration duration = new Duration();
    protected final Instant date = new Instant();
    protected boolean override;
    
    public DurationDate(Duration duration, Instant date, boolean override) {
        this.duration.set(duration);
        this.date.set(date);
        this.override = override;
    }

    public DurationDate(Duration duration, Instant date) {
        this(duration, date, false);
    }
    
    public DurationDate(Duration duration) {
        this(duration, null);
    }
    
    public DurationDate(Instant date) {
        this(null, date);
    }
    
    public DurationDate() {
        this(null, null, false);
    }
    
    public void set(DurationDate durationDate) {
        this.setDuration(durationDate.getDuration());
        this.setDate(durationDate.getDate());
        this.override = durationDate.isOverride();
    }
    
    public void setDuration(Duration duration) {
        this.duration.set(duration);
    }
    
    public void setDate(Instant date) {
        this.date.set(date);
    }

    public Duration getDuration() {
        return duration.clone();
    }

    public Instant getDate() {
        return date.clone();
    }
    
    public Instant getDate(Instant previous) {
        if (this.date.greaterThan(new Date())) {
            return getDate();
        }
        
        this.date.set(previous).add(this.duration);
        return getDate();
    }

    public boolean isOverride() {
        return override;
    }
    
    @Override
    public DurationDate clone() {
        return new DurationDate(this.duration, this.date, this.override);
    }
}
