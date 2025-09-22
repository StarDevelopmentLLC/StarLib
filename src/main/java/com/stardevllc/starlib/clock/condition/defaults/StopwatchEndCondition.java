package com.stardevllc.starlib.clock.condition.defaults;

import com.stardevllc.starlib.clock.condition.ClockEndCondition;
import com.stardevllc.starlib.clock.snapshot.StopwatchSnapshot;

/**
 * Represents an end condition for a stopwatch that ends when the endtime is reached
 */
public class StopwatchEndCondition implements ClockEndCondition<StopwatchSnapshot> {
    
    /**
     * Constructs a stopwatch end condition
     */
    public StopwatchEndCondition() {
    }
    
    @Override
    public boolean shouldEnd(StopwatchSnapshot snapshot) {
        return snapshot.getTime() >= snapshot.getEndTime();
    }
}
