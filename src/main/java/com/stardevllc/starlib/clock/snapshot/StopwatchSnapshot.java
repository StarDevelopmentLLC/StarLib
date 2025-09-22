package com.stardevllc.starlib.clock.snapshot;

/**
 * Represents a snapshot of a stopwatch
 */
public class StopwatchSnapshot extends ClockSnapshot {
    
    private final long endTime;
    
    /**
     * Constructs a new snapshot
     *
     * @param time        the current time
     * @param endTime     The end time
     * @param paused      The paused flag
     * @param countAmount The count amount
     */
    public StopwatchSnapshot(long time, long endTime, boolean paused, long countAmount) {
        super(time, paused, countAmount);
        this.endTime = endTime;
    }
    
    /**
     * Gets the end time of the stopwatch
     *
     * @return The end time
     */
    public long getEndTime() {
        return endTime;
    }
}
