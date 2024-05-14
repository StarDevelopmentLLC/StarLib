package com.stardevllc.starlib.clock.condition.defaults;

import com.stardevllc.starlib.clock.condition.ClockEndCondition;
import com.stardevllc.starlib.clock.snapshot.StopwatchSnapshot;

public class StopwatchEndCondition implements ClockEndCondition<StopwatchSnapshot> {
    @Override
    public boolean shouldEnd(StopwatchSnapshot snapshot) {
        return snapshot.getTime() >= snapshot.getEndTime();
    }
}
