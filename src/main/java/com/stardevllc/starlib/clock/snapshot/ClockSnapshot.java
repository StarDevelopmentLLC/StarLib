package com.stardevllc.starlib.clock.snapshot;

/**
 * Represents a read-only copy of a clock
 */
public abstract class ClockSnapshot {
    /**
     * The time of the clock
     */
    protected final long time;
    
    /**
     * The paused status of the clock
     */
    protected final boolean paused;
    
    /**
     * The count amount of the clock
     */
    protected final long countAmount;
    
    /**
     * Constructs a clock snapshot
     * @param time The time
     * @param paused The paused flag
     * @param countAmount The count amount
     */
    public ClockSnapshot(long time, boolean paused, long countAmount) {
        this.time = time;
        this.paused = paused;
        this.countAmount = countAmount;
    }
    
    /**
     * The time
     * @return The time
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Paused status
     * @return Paused status
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Count amount
     * @return Count amount
     */
    public long getCountAmount() {
        return countAmount;
    }
}
