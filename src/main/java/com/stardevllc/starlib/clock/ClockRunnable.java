package com.stardevllc.starlib.clock;

import java.util.ArrayList;

/**
 * A runnable that makes clocks do things. You can have your own if you want, Its a runnable to allow flexibility
 */
public final class ClockRunnable implements Runnable {
    public enum Status {
        TICKING_CLOCKS, 
        UNDEFINED
    }
    
    private ClockManager clockManager;
    private Status status;
    
    /**
     * Constructs a new clock runnable
     * @param clockManager The clock manager
     */
    public ClockRunnable(ClockManager clockManager) {
        this.clockManager = clockManager;
    }
    
    @Override
    public void run() {
        this.status = Status.TICKING_CLOCKS;
        for (Clock<?> clock : new ArrayList<>(clockManager.getClocks())) {
            clock.tick();
            
            if (clock.isCancelled()) {
                clockManager.removeClock(clock);
            }
        }
        this.status = Status.UNDEFINED;
    }
}