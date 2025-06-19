package com.stardevllc.clock;

import java.util.ArrayList;

/**
 * A runnable that makes clocks do things. You can have your own if you want, Its a runnable to allow flexibility
 */
public final class ClockRunnable implements Runnable {
    
    private ClockManager clockManager;
    
    public ClockRunnable(ClockManager clockManager) {
        this.clockManager = clockManager;
    }
    
    @Override
    public void run() {
        for (Clock<?> clock : new ArrayList<>(clockManager.getClocks())) {
            clock.tick();
            
            if (clock.isCancelled()) {
                clockManager.removeClock(clock);
            }
        }
    }
}