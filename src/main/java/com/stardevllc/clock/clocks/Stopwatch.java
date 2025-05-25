package com.stardevllc.clock.clocks;

import com.stardevllc.clock.Clock;
import com.stardevllc.clock.callback.CallbackHolder;
import com.stardevllc.clock.property.ClockLongProperty;
import com.stardevllc.clock.snapshot.StopwatchSnapshot;
import com.stardevllc.observable.property.LongProperty;

public class Stopwatch extends Clock<StopwatchSnapshot> {
    protected final ClockLongProperty endTime;
    protected final ClockLongProperty startTime;
    
    public Stopwatch(long startTime, long endTime, long countAmount) {
        super(startTime, countAmount);
        this.startTime = new ClockLongProperty(this, "startTime", startTime);
        this.endTime = new ClockLongProperty(this, "endTime", endTime);
        this.endTime.addListener(e -> unpause());
    }
    
    public Stopwatch(long endTime, long countAmount) {
        this(0L, endTime, countAmount);
    }
    
    @Override
    protected boolean shouldCallback(CallbackHolder<StopwatchSnapshot> holder) {
        if (!holder.isRepeating()) {
            if (holder.hasRun()) {
                return false;
            }
            
            return this.time.get() > holder.getPeriod();
        }
        
        long elapsed = this.startTime.get() - this.time.get();
        long periodsElapsed = elapsed / holder.getPeriod() - 1;
        long nextRun = this.startTime.get() - ((periodsElapsed + 1) * holder.getPeriod());
        
        return this.time.get() == nextRun;
    }
    
    @Override
    protected long getNextRun(CallbackHolder<StopwatchSnapshot> holder) {
        if (!holder.isRepeating()) {
            return holder.getPeriod();
        }
        
        long elapsed = this.startTime.get() - this.time.get();
        long periodsElapsed = elapsed / holder.getPeriod() - 1;
        return this.startTime.get() - ((periodsElapsed + 1) * holder.getPeriod());
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