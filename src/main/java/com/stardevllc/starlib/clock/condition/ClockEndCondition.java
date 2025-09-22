package com.stardevllc.starlib.clock.condition;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

/**
 * See {@link Clock#setEndCondition(ClockEndCondition)}
 * @param <T> The snapshot type
 */
@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    
    /**
     * Returns if the clock should end
     * @param snapshot The clock snapshot
     * @return true if the clock should end or false if it should keep going
     */
    boolean shouldEnd(T snapshot);
}
