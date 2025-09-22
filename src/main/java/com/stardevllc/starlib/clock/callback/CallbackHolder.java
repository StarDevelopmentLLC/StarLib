package com.stardevllc.starlib.clock.callback;

import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

import java.util.UUID;

/**
 * Holds important information for a clock callback
 *
 * @param <T> The snapshot type
 */
public class CallbackHolder<T extends ClockSnapshot> {
    private final ClockCallback<T> callback;
    private final UUID callbackId;
    private final CallbackPeriod period;
    private boolean repeating = true, hasRun;
    
    /**
     * Constructs a new CallbackHOlder
     *
     * @param callback   The callback instance
     * @param callbackId The id of the callback
     * @param period     The period of the callback
     */
    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, CallbackPeriod period) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
    }
    
    /**
     * Constructs a new CallbackHOlder
     *
     * @param callback   The callback instance
     * @param callbackId The id of the callback
     * @param period     The period of the callback
     * @param repeating  If the callback is repeating
     */
    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, CallbackPeriod period, boolean repeating) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
        this.repeating = repeating;
    }
    
    /**
     * Gets the callback
     *
     * @return The callback
     */
    public ClockCallback<T> getCallback() {
        return callback;
    }
    
    /**
     * Gets the period
     *
     * @return The period
     */
    public CallbackPeriod getPeriod() {
        return period;
    }
    
    /**
     * Gets if the callback is repeating or not
     *
     * @return The repeating status
     */
    public boolean isRepeating() {
        return repeating;
    }
    
    /**
     * Flag to determine if it has been run
     *
     * @return If the callback has been run
     */
    public boolean hasRun() {
        return hasRun;
    }
    
    /**
     * Sets the hasRun flag
     *
     * @param hasRun The value for the flag
     */
    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
}