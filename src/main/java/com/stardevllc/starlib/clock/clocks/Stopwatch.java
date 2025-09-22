package com.stardevllc.starlib.clock.clocks;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.callback.CallbackHolder;
import com.stardevllc.starlib.clock.snapshot.StopwatchSnapshot;
import com.stardevllc.starlib.observable.property.readwrite.ReadWriteLongProperty;

import java.util.UUID;

/**
 * Count up clock
 */
public class Stopwatch extends Clock<StopwatchSnapshot> {
    /**
     * The time to end the stopwatch at
     */
    protected final ReadWriteLongProperty endTime;
    
    /**
     * The time to start the stopwatch et
     */
    protected final ReadWriteLongProperty startTime;
    
    /**
     * Constructs a new stopwatch
     *
     * @param uuid        The uuid of the stopwatch
     * @param startTime   The start time of the stopwatch
     * @param endTime     The end time of the stopwatch
     * @param countAmount The amount to count by
     */
    public Stopwatch(UUID uuid, long startTime, long endTime, long countAmount) {
        this(uuid, "", startTime, endTime, countAmount);
    }
    
    /**
     * Constructs a new stopwatch
     *
     * @param uuid        The uuid of the stopwatch
     * @param name        The name of the stopwatch
     * @param startTime   The start time of the stopwatch
     * @param endTime     The end time of the stopwatch
     * @param countAmount The amount to count by
     */
    public Stopwatch(UUID uuid, String name, long startTime, long endTime, long countAmount) {
        super(uuid, name, startTime, countAmount);
        this.startTime = new ReadWriteLongProperty(this, "startTime", startTime);
        this.endTime = new ReadWriteLongProperty(this, "endTime", endTime);
        this.endTime.addListener(e -> unpause());
    }
    
    /**
     * Constructs a new stopwatch
     *
     * @param uuid        The uuid of the stopwatch
     * @param endTime     The end time of the stopwatch
     * @param countAmount The amount to count by
     */
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
    
    /**
     * Gets the end time
     *
     * @return The end time
     */
    public long getEndTime() {
        return endTime.get();
    }
    
    /**
     * Sets the end time
     *
     * @param endTime The end time
     */
    public void setEndTime(long endTime) {
        this.endTime.setValue(endTime);
    }
    
    /**
     * Gets the endTime property
     *
     * @return The endtime property
     */
    public ReadWriteLongProperty endTimeProperty() {
        return this.endTime;
    }
    
    /**
     * Gets the start time property
     *
     * @return The start time property
     */
    public ReadWriteLongProperty startTimeProperty() {
        return this.startTime;
    }
}