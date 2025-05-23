package com.stardevllc.clock.callback;

import com.stardevllc.clock.snapshot.ClockSnapshot;

import java.util.UUID;

public class CallbackHolder<T extends ClockSnapshot> {
    protected final ClockCallback<T> callback;
    protected final UUID callbackId;
    protected final long period;
    protected boolean repeating = true, hasRun;
    
    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, long period) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
    }

    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, long period, boolean repeating) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
        this.repeating = repeating;
    }

    public ClockCallback<T> getCallback() {
        return callback;
    }
    
    public long getPeriod() {
        return period;
    }
    
    public boolean isRepeating() {
        return repeating;
    }
    
    public boolean hasRun() {
        return hasRun;
    }
    
    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
}