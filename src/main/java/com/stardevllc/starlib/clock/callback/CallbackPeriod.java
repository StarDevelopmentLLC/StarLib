package com.stardevllc.starlib.clock.callback;

/**
 * Allows for customization of a period in a clock. It's an interface to allow far more dynamic configuration of periods
 */
@FunctionalInterface
public interface CallbackPeriod {
    /**
     * Get the time of the period
     *
     * @return The period time
     */
    long get();
}
