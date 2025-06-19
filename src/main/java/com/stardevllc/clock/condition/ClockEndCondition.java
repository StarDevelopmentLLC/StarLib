package com.stardevllc.clock.condition;

import com.stardevllc.clock.snapshot.ClockSnapshot;

/**
 * See {@link com.stardevllc.clock.Clock#setEndCondition(ClockEndCondition)}
 * @param <T> The snapshot type
 */
@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    boolean shouldEnd(T snapshot);
}
