package com.stardevllc.starlib.clock.condition.defaults;

import com.stardevllc.starlib.clock.condition.ClockEndCondition;
import com.stardevllc.starlib.clock.snapshot.TimerSnapshot;

public class TimerEndCondition implements ClockEndCondition<TimerSnapshot> {
    @Override
    public boolean shouldEnd(TimerSnapshot snapshot) {
        return snapshot.getTime() <= 0;
    }
}
