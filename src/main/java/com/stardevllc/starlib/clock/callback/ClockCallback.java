package com.stardevllc.starlib.clock.callback;

import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

/**
 * This class is a functional interface used to perform timed actions within clocks. It pretty much is the backbone of how to do anything
 *
 * @param <T> The type of the ClockSnapshot
 */
@FunctionalInterface
public interface ClockCallback<T extends ClockSnapshot> {
    /**
     * The method that is called when a clock determine that this can run
     *
     * @param snapshot The snapshot of the clock
     */
    void callback(T snapshot);
    
    /**
     * The period, time in milliseconds that this callback runs at relative to the clock time
     *
     * @return The period
     */
    default CallbackPeriod getPeriod() {
        return () -> 1L;
    }
    
    /**
     * If this callback repeats
     *
     * @return The repeating flag
     */
    default boolean isRepeating() {
        return true;
    }
    
    /**
     * The name of the callback
     *
     * @return The name
     */
    default String getName() {
        return "";
    }
}