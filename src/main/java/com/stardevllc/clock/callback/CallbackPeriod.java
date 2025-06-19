package com.stardevllc.clock.callback;

/**
 * Allows for customization of a period in a clock. It's an interface to allow far more dynamic configuration of periods
 */
@FunctionalInterface
public interface CallbackPeriod {
    long get();
}
