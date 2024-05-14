package com.stardevllc.starlib.clock;

import com.stardevllc.starlib.clock.callback.CallbackHolder;
import com.stardevllc.starlib.clock.callback.ClockCallback;
import com.stardevllc.starlib.clock.condition.ClockEndCondition;
import com.stardevllc.starlib.clock.property.ClockBooleanProperty;
import com.stardevllc.starlib.clock.property.ClockLongProperty;
import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;
import com.stardevllc.starlib.observable.property.writable.BooleanProperty;
import com.stardevllc.starlib.observable.property.writable.LongProperty;
import com.stardevllc.starlib.time.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Clock<T extends ClockSnapshot> {
    protected final ClockLongProperty timeProperty;
    protected final ClockBooleanProperty pausedProperty;
    protected final ClockBooleanProperty cancelledProperty;
    protected Map<UUID, CallbackHolder<T>> callbacks = new HashMap<>();
    protected final long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    public Clock(long time, long countAmount) {
        this.timeProperty = new ClockLongProperty(this, "time", time);
        this.pausedProperty = new ClockBooleanProperty(this, "paused", true);
        this.cancelledProperty = new ClockBooleanProperty(this, "cancelled", false);
        this.countAmount = countAmount;
        
        this.timeProperty.addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                unpause();
            }
        });
    }

    public void tick() {
        if (isPaused() || isCancelled()) {
            return;
        }
        
        T snapshot = createSnapshot();
        callback(snapshot);
        long oldTime = timeProperty.get();
        count();
        long newTime = timeProperty.get();
        
        if (oldTime != newTime) {
            unpause();
        } else {
            pause();
        }
    }
    
    public final long getCountAmount() {
        return countAmount;
    }
    
    protected abstract void count();
    
    public abstract T createSnapshot();
    
    protected abstract boolean shouldCallback(CallbackHolder<T> holder);
    
    public boolean shouldCallback(UUID callbackUUID) {
        CallbackHolder<T> callbackHolder = this.callbacks.get(callbackUUID);
        if (callbackHolder == null) {
            return false;
        }
        
        return shouldCallback(callbackHolder);
    }
    
    protected abstract long getNextRun(CallbackHolder<T> holder);
    
    public long getNextRun(UUID callbackUUID) {
        CallbackHolder<T> callbackHolder = this.callbacks.get(callbackUUID);
        if (callbackHolder == null) {
            return 0;
        }
        
        return getNextRun(callbackHolder);
    }
    
    public void callback(T snapshot) {
        if (this.callbacks.isEmpty()) {
            return;
        }
        
        for (CallbackHolder<T> holder : this.callbacks.values()) {
            ClockCallback<T> callback = holder.getCallback();
            if (callback == null) {
                continue;
            }
            
            if (holder.getPeriod() <= 0) {
                continue;
            }
            
            if (!shouldCallback(holder)) {
                continue;
            }
            
            holder.setLastRun(timeProperty.get());
            callback.callback(snapshot);
        }
        
        if (endCondition != null) {
            if (endCondition.shouldEnd(snapshot)) {
                cancel();
            }
        } 
    }
    
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    public void pause() {
        this.pausedProperty.set(true);
    }
    
    public void unpause() {
        this.pausedProperty.set(false);
    }
    
    public void cancel() {
        this.cancelledProperty.set(true);
    }
    
    public void uncancel() {
        this.cancelledProperty.set(false);
    }
    
    public boolean isCancelled() {
        return cancelledProperty.get();
    }
    
    public long getTime() {
        return timeProperty.get();
    }
    
    public boolean isPaused() {
        return pausedProperty.get();
    }
    
    public void addTime(long time) {
        this.timeProperty.setValue(timeProperty.get() + time);
    }
    
    public void removeTime(long time) {
        this.timeProperty.setValue(timeProperty.get() - time);
    }
    
    public void setTime(long time) {
        this.timeProperty.setValue(time);
    }
    
    public UUID addCallback(ClockCallback<T> callback, long runAtTime) {
        return addCallback(callback, runAtTime, false);
    }
    
    public UUID addRepeatingCallback(ClockCallback<T> callback, long period) {
        return addCallback(callback, period, true);
    }

    public UUID addCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, unit.toMillis(unitTime), false);
    }

    public UUID addRepeatingCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, unit.toMillis(unitTime), true);
    }

    public UUID addCallback(ClockCallback<T> callback, long period, boolean repeating) {
        if (callback == null) {
            return null;
        }
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (this.callbacks.containsKey(uuid));

        this.callbacks.put(uuid, new CallbackHolder<>(callback, uuid, period, repeating));
        return uuid;
    }
    
    public UUID addCallback(ClockCallback<T> callback) {
        return addCallback(callback, callback.getPeriod());
    }
    
    public void removeCallback(UUID uuid) {
        this.callbacks.remove(uuid);
    }
    
    public void setEndCondition(ClockEndCondition<T> endCondition) {
        this.endCondition = endCondition;
    }
    
    public ClockEndCondition<T> getEndCondition() {
        return endCondition;
    }
    
    public ClockCallback<T> getCallback(UUID uuid) {
        CallbackHolder<T> holder = this.callbacks.get(uuid);
        if (holder != null) {
            return holder.getCallback();
        }
        
        return null;
    }
    
    public LongProperty timeProperty() {
        return this.timeProperty;
    }
    
    public BooleanProperty pausedProperty() {
        return this.pausedProperty;
    }
    
    public BooleanProperty cancelledProperty() {
        return this.cancelledProperty;
    }
}