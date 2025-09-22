package com.stardevllc.starlib.clock.snapshot;

/**
 * Represents the snapshot of a Timer
 */
public class TimerSnapshot extends ClockSnapshot {
    
    /**
     * The length of the timer
     */
    protected final long length;
    
    /**
     * Constructs a new snapshot
     *
     * @param time        The time
     * @param paused      The paused flag
     * @param length      The length
     * @param countAmount The count amount
     */
    public TimerSnapshot(long time, boolean paused, long length, long countAmount) {
        super(time, paused, countAmount);
        this.length = length;
    }
    
    /**
     * Gets the length of the timer
     *
     * @return The length
     */
    public long getLength() {
        return length;
    }
}
