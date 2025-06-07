package com.stardevllc.clock.clocks;

import com.stardevllc.clock.Clock;
import com.stardevllc.clock.callback.CallbackHolder;
import com.stardevllc.clock.property.ClockLongProperty;
import com.stardevllc.clock.snapshot.StopwatchSnapshot;
import com.stardevllc.observable.property.LongProperty;

import java.util.UUID;

public class Stopwatch extends Clock<StopwatchSnapshot> {
    protected final ClockLongProperty endTime;
    protected final ClockLongProperty startTime;
    
    public Stopwatch(UUID uuid, long startTime, long endTime, long countAmount) {
        this(uuid, "", startTime, endTime, countAmount);
    }
    
    public Stopwatch(UUID uuid, String name, long startTime, long endTime, long countAmount) {
        super(uuid, name, startTime, countAmount);
        this.startTime = new ClockLongProperty(this, "startTime", startTime);
        this.endTime = new ClockLongProperty(this, "endTime", endTime);
        this.endTime.addListener(e -> unpause());
    }
    
    public Stopwatch(UUID uuid, long endTime, long countAmount) {
        this(uuid, 0L, endTime, countAmount);
    }
    
    @Override
    protected boolean shouldCallback(CallbackHolder<StopwatchSnapshot> holder) {
        if (!holder.isRepeating()) {
            if (holder.hasRun()) {
                return false;
            }
            
            return this.time.get() > holder.getPeriod().get();
        }
        
        return this.time.get() == getNextRun(holder);
    }
    
    @Override
    protected long getNextRun(CallbackHolder<StopwatchSnapshot> holder) {
        long period = holder.getPeriod().get();
        
        if (!holder.isRepeating()) {
            return period;
        }
        
        long elapsed = this.time.get() - this.startTime.get();
        long periodsElapsed = elapsed / period;
        if (elapsed % period != 0) {
            return this.startTime.get() + (periodsElapsed + 1) * period;
        } else {
            return this.startTime.get() + periodsElapsed * period;
        }
    }

    @Override
    public StopwatchSnapshot createSnapshot() {
        return new StopwatchSnapshot(getTime(), getEndTime(), isPaused(), getCountAmount());
    }

    @Override
    protected void count() {
        if (this.endTime.get() == 0L) {
            this.time.setValue(this.time.get() + this.countAmount);
        } else {
            this.time.setValue(Math.min(getTime() + countAmount, this.getEndTime()));
        }
    }

    @Override
    public Stopwatch start() {
        return (Stopwatch) super.start();
    }
    
    public long getEndTime() {
        return endTime.get();
    }
    
    public void setEndTime(long endTime) {
        this.endTime.setValue(endTime);
    }
    
    public LongProperty endTimeProperty() {
        return this.endTime;
    }
    
    public LongProperty startTimeProperty() {
        return this.startTime;
    }
}