package com.stardevllc.starlib.clock.clocks;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.callback.CallbackHolder;
import com.stardevllc.starlib.clock.property.ClockLongProperty;
import com.stardevllc.starlib.clock.snapshot.StopwatchSnapshot;
import com.stardevllc.starlib.observable.property.writable.LongProperty;

public class Stopwatch extends Clock<StopwatchSnapshot> {
    protected final ClockLongProperty endTimeProperty;
    
    public Stopwatch(long endTime, long countAmount) {
        super(0L, countAmount);
        this.endTimeProperty = new ClockLongProperty(this, "endTime", endTime);
        this.endTimeProperty.addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                unpause();
            }
        });
    }

    @Override
    protected boolean shouldCallback(CallbackHolder<StopwatchSnapshot> holder) {
        long lastRun = holder.getLastRun(); //Variable to easily access when it ran last
        if (!holder.isRepeating()) {
            long run = holder.getPeriod(); //This is for when the non-repeating callback should run based on the length
            return lastRun == -1 && this.getTime() >= run;
        } else {
            if (holder.getLastRun() == -1) {
                return holder.getPeriod() >= this.getTime();
            } else {
                long nextRun = holder.getLastRun() + holder.getPeriod();
                return this.getTime() >= nextRun;
            }
        }
    }

    @Override
    protected long getNextRun(CallbackHolder<StopwatchSnapshot> holder) {
        if (!holder.isRepeating()) {
            return holder.getPeriod();
        }

        if (holder.getLastRun() == -1) {
            return this.getTime() + holder.getPeriod();
        } else {
            return holder.getLastRun() + holder.getPeriod();
        }
    }

    @Override
    public StopwatchSnapshot createSnapshot() {
        return new StopwatchSnapshot(getTime(), getEndTime(), isPaused(), getCountAmount());
    }

    @Override
    protected void count() {
        this.timeProperty.setValue(Math.min(getTime() + countAmount, this.getEndTime()));
    }

    @Override
    public Stopwatch start() {
        return (Stopwatch) super.start();
    }
    
    public long getEndTime() {
        return endTimeProperty.get();
    }
    
    public void setEndTime(long endTime) {
        this.endTimeProperty.setValue(endTime);
    }
    
    public LongProperty endTimeProperty() {
        return this.endTimeProperty;
    }
}