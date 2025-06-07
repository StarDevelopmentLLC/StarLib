package com.stardevllc.clock;

import com.stardevllc.clock.callback.*;
import com.stardevllc.clock.condition.ClockEndCondition;
import com.stardevllc.clock.property.ClockBooleanProperty;
import com.stardevllc.clock.property.ClockLongProperty;
import com.stardevllc.clock.snapshot.ClockSnapshot;
import com.stardevllc.observable.property.*;
import com.stardevllc.time.TimeUnit;

import java.util.*;

public abstract class Clock<T extends ClockSnapshot> {
    protected final UUIDProperty uniqueId;
    protected final StringProperty name;
    protected final ClockLongProperty time;
    protected final ClockBooleanProperty paused;
    protected final ClockBooleanProperty cancelled;
    protected Map<UUID, CallbackHolder<T>> callbacks = new LinkedHashMap<>();
    protected final long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    public Clock(UUID uuid, long time, long countAmount) {
        this(uuid, "", time, countAmount);
    }
    
    public Clock(UUID uuid, String name, long time, long countAmount) {
        this.uniqueId = new UUIDProperty(this, "uniqueid", uuid);
        this.name = new StringProperty(this, "name", name);
        this.time = new ClockLongProperty(this, "time", time);
        this.paused = new ClockBooleanProperty(this, "paused", true);
        this.cancelled = new ClockBooleanProperty(this, "cancelled", false);
        this.countAmount = countAmount;
        
        this.time.addListener(e -> unpause());
    }

    public void tick() {
        if (isPaused() || isCancelled()) {
            return;
        }

        T snapshot = createSnapshot();
        callback(snapshot);
        long oldTime = time.get();
        count();
        long newTime = time.get();
        
        if (oldTime != newTime) {
            unpause();
        } else {
            pause();
        }

        if (endCondition != null) {
            if (endCondition.shouldEnd(snapshot)) {
                cancel();
            }
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
            
            if (holder.getPeriod().get() <= 0) {
                continue;
            }
            
            if (!shouldCallback(holder)) {
                continue;
            }
            
            callback.callback(snapshot);
            holder.setHasRun(true);
        }
    }
    
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    public void pause() {
        this.paused.set(true);
    }
    
    public void unpause() {
        this.paused.set(false);
    }
    
    public void cancel() {
        this.cancelled.set(true);
    }
    
    public void uncancel() {
        this.cancelled.set(false);
    }
    
    public boolean isCancelled() {
        return cancelled.get();
    }
    
    public long getTime() {
        return time.get();
    }
    
    public boolean isPaused() {
        return paused.get();
    }
    
    public void addTime(long time) {
        this.time.setValue(this.time.get() + time);
    }
    
    public void removeTime(long time) {
        this.time.setValue(this.time.get() - time);
    }
    
    public void setTime(long time) {
        this.time.setValue(time);
    }
    
    public UUID addCallback(ClockCallback<T> callback, long runAtTime) {
        return addCallback(callback, () -> runAtTime, false);
    }
    
    public UUID addCallback(ClockCallback<T> callback, CallbackPeriod runAtTime) {
        return addCallback(callback, runAtTime, false);
    }
    
    public UUID addRepeatingCallback(ClockCallback<T> callback, long period) {
        return addCallback(callback, () -> period, true);
    }
    
    public UUID addRepeatingCallback(ClockCallback<T> callback, CallbackPeriod period) {
        return addCallback(callback, period, true);
    }

    public UUID addCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, () -> unit.toMillis(unitTime), false);
    }

    public UUID addRepeatingCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, () -> unit.toMillis(unitTime), true);
    }

    public UUID addCallback(ClockCallback<T> callback, CallbackPeriod period, boolean repeating) {
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
    
    public UUID addRepeatingCallback(ClockCallback<T> callback) {
        return addRepeatingCallback(callback, callback.getPeriod());
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
    
    public UUID getUniqueId() {
        return this.uniqueId.get();
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public LongProperty timeProperty() {
        return this.time;
    }
    
    public BooleanProperty pausedProperty() {
        return this.paused;
    }
    
    public BooleanProperty cancelledProperty() {
        return this.cancelled;
    }
    
    public UUIDProperty uuidProperty() {
        return this.uniqueId;
    }
    
    public StringProperty nameProperty() {
        return this.name;
    }
}