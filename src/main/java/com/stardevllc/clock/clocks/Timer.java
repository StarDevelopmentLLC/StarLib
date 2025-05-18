package com.stardevllc.clock.clocks;

import com.stardevllc.clock.Clock;
import com.stardevllc.clock.callback.CallbackHolder;
import com.stardevllc.clock.property.ClockLongProperty;
import com.stardevllc.clock.snapshot.TimerSnapshot;
import com.stardevllc.observable.property.LongProperty;

public class Timer extends Clock<TimerSnapshot> {
    
    protected final ClockLongProperty lengthProperty;
    
    public Timer(long length, long countAmount) {
        super(length, countAmount);
        this.lengthProperty = new ClockLongProperty(this, "length", length);
        this.lengthProperty.addListener(e -> unpause());
    }
    
    @Override
    protected boolean shouldCallback(CallbackHolder<TimerSnapshot> holder) {
        long elapsed = this.lengthProperty.get() - this.time.get();
        long periodsElapsed = elapsed / holder.getPeriod() - 1;
        long nextRun = this.lengthProperty.get() - ((periodsElapsed + 1) * holder.getPeriod());
        
        return this.time.get() == nextRun;
    }
    
    @Override
    protected long getNextRun(CallbackHolder<TimerSnapshot> holder) {
        if (!holder.isRepeating()) {
            return holder.getPeriod();
        }
        
        long elapsed = this.lengthProperty.get() - this.time.get();
        long periodsElapsed = elapsed / holder.getPeriod();
        return this.lengthProperty.get() - ((periodsElapsed + 1) * holder.getPeriod());
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