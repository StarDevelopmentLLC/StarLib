package com.stardevllc.starlib.clock.condition;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

/**
 * See {@link Clock#setEndCondition(ClockEndCondition)}
 * @param <T> The snapshot type
 */
@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    boolean shouldEnd(T snapshot);
}
