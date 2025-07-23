package com.stardevllc.starlib.clock.clocks;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.callback.CallbackHolder;
import com.stardevllc.starlib.clock.property.ClockLongProperty;
import com.stardevllc.starlib.clock.snapshot.TimerSnapshot;
import com.stardevllc.starlib.observable.property.LongProperty;

import java.util.UUID;

/**
 * Count down clock
 */
public class Timer extends Clock<TimerSnapshot> {
    
    protected final ClockLongProperty lengthProperty;
    
    public Timer(UUID uuid, long length, long countAmount) {
        this(uuid, "", length, countAmount);
    }
    
    public Timer(UUID uuid, String name, long length, long countAmount) {
        super(uuid, name, length, countAmount);
        this.lengthProperty = new ClockLongProperty(this, "length", length);
        this.lengthProperty.addListener(e -> unpause());
    }
    
    @Override
    protected boolean shouldCallback(CallbackHolder<TimerSnapshot> holder) {
        if (!holder.isRepeating()) {
            if (holder.hasRun()) {
                return false;
            }
            
            return this.time.get() < holder.getPeriod().get();
        }
        
        return this.time.get() == getNextRun(holder);
    }
    
    @Override
    protected long getNextRun(CallbackHolder<TimerSnapshot> holder) {
        long period = holder.getPeriod().get();
        
        if (!holder.isRepeating()) {
            return period;
        }
        
        long elapsed = this.lengthProperty.get() - this.time.get();
        long periodsElapsed = elapsed / period;
        if (elapsed % period != 0) {
            return this.lengthProperty.get() - (periodsElapsed + 1) * period;
        } else {
            return this.lengthProperty.get() - periodsElapsed * period;
        }
    }
    
    @Override
    protected void count() {
        this.time.setValue(Math.max(0, getTime() - countAmount));
    }
    
    public long getLength() {
        return lengthProperty.get();
    }
    
    public void reset() {
        setTime(getLength());
    }
    
    public void setLength(long length) {
        long elapsed = this.getLength() - this.getTime();
        this.lengthProperty.setValue(length);
        this.time.setValue(Math.max(this.getLength() - elapsed, 0));
    }
    
    public void setLengthAndReset(long length) {
        this.lengthProperty.setValue(length);
        this.reset();
    }
    
    public void addLength(long length) {
        setLength(this.getLength() + length);
    }
    
    public void removeLength(long length) {
        if (length - this.getLength() < 0) {
            length = this.getLength();
        }
        
        setLength(this.getLength() - length);
    }
    
    @Override
    public Timer start() {
        return (Timer) super.start();
    }
    
    @Override
    public TimerSnapshot createSnapshot() {
        return new TimerSnapshot(this.getTime(), isPaused(), this.getLength(), getCountAmount());
    }
    
    public LongProperty lengthProperty() {
        return this.lengthProperty;
    }
}