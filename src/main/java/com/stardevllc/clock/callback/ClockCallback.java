package com.stardevllc.clock.callback;

import com.stardevllc.clock.snapshot.ClockSnapshot;

/**
 * This class is a functional interface used to perform timed actions within clocks. It pretty much is the backbone of how to do anything
 * @param <T> The type of the ClockSnapshot
 */
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