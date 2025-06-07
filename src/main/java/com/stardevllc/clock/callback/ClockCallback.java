package com.stardevllc.clock.callback;

import com.stardevllc.clock.snapshot.ClockSnapshot;

@FunctionalInterface
public interface ClockCallback<T extends ClockSnapshot> {
    void callback(T snapshot);
    
    default CallbackPeriod getPeriod() {
        return () -> 1L;
    }
    
    default boolean isRepeating() {
        return true;
    }
    
    default String getName() {
        return "";
    }
}