package com.stardevllc.starlib.clock.clocks;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.callback.CallbackHolder;
import com.stardevllc.starlib.clock.snapshot.TimerSnapshot;
import com.stardevllc.starlib.observable.property.readwrite.ReadWriteLongProperty;

import java.util.UUID;

/**
 * Count down clock
 */
public class Timer extends Clock<TimerSnapshot> {
    
    /**
     * The length of the timer, or start point
     */
    protected final ReadWriteLongProperty lengthProperty;
    
    /**
     * Constructs a new timer
     *
     * @param uuid        The uuid of the timer
     * @param length      The length of the timer
     * @param countAmount The amount to count by
     */
    public Timer(UUID uuid, long length, long countAmount) {
        this(uuid, "", length, countAmount);
    }
    
    /**
     * Constructs a new timer
     *
     * @param uuid        The uuid of the timer
     * @param name        The name of the timer
     * @param length      The length of the timer
     * @param countAmount The amount to count by
     */
    public Timer(UUID uuid, String name, long length, long countAmount) {
        super(uuid, name, length, countAmount);
        this.lengthProperty = new ReadWriteLongProperty(this, "length", length);
        this.lengthProperty.addListener((observable, oldValue, newValue) -> unpause());
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
    
    /**
     * Gets the length of the timer
     *
     * @return The length
     */
    public long getLength() {
        return lengthProperty.get();
    }
    
    /**
     * Resets back to the start point
     */
    public void reset() {
        setTime(getLength());
    }
    
    /**
     * Sets the length and adjusts according to elapsed time
     *
     * @param length The new length
     */
    public void setLength(long length) {
        long elapsed = this.getLength() - this.getTime();
        this.lengthProperty.setValue(length);
        this.time.setValue(Math.max(this.getLength() - elapsed, 0));
    }
    
    /**
     * Same as {@link #setLength(long)} but resets back to the st art
     *
     * @param length The new length
     */
    public void setLengthAndReset(long length) {
        this.lengthProperty.setValue(length);
        this.reset();
    }
    
    /**
     * Does {@code setLength(this.getLength() + length)}
     *
     * @param length The length
     */
    public void addLength(long length) {
        setLength(this.getLength() + length);
    }
    
    /**
     * Does {@code setLength(this.getLength() - length} with a check to not go below 0
     *
     * @param length The length
     */
    public void removeLength(long length) {
        if (length - this.getLength() < 0) {
            length = this.getLength();
        }
        
        setLength(this.getLength() - length);
    }
    
    /**
     * Signals that this timer should start running
     *
     * @return This timer
     */
    @Override
    public Timer start() {
        return (Timer) super.start();
    }
    
    /**
     * Creates a snapshot based on this timer
     *
     * @return The snapshot
     */
    @Override
    public TimerSnapshot createSnapshot() {
        return new TimerSnapshot(this.getTime(), isPaused(), this.getLength(), getCountAmount());
    }
    
    /**
     * Gets the length property of this timer
     *
     * @return The length property
     */
    public ReadWriteLongProperty lengthProperty() {
        return this.lengthProperty;
    }
}