package com.stardevllc.starlib.clock.condition.defaults;

import com.stardevllc.starlib.clock.condition.ClockEndCondition;
import com.stardevllc.starlib.clock.snapshot.TimerSnapshot;

/**
 * Represents an end condtion that stops the timer at 0 and cancels it
 */
public class TimerEndCondition implements ClockEndCondition<TimerSnapshot> {
    
    /**
     * Constructs a TimerEndCondition
     */
    public TimerEndCondition() {
    }
    
    @Override
    public boolean shouldEnd(TimerSnapshot snapshot) {
        return snapshot.getTime() <= 0;
    }
}
