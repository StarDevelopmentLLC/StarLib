package com.stardevllc.starlib.clock.condition;

import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    boolean shouldEnd(T snapshot);
}
