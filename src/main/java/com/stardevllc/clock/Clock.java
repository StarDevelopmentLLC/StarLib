package com.stardevllc.clock;

import com.stardevllc.builder.IBuilder;
import com.stardevllc.clock.callback.*;
import com.stardevllc.clock.condition.ClockEndCondition;
import com.stardevllc.clock.property.ClockBooleanProperty;
import com.stardevllc.clock.property.ClockLongProperty;
import com.stardevllc.clock.snapshot.ClockSnapshot;
import com.stardevllc.observable.property.*;
import com.stardevllc.time.TimeUnit;

import java.util.*;

/**
 * <pre>A class that represents a clock. There are two direct concrete implementations of this in {@link com.stardevllc.clock.clocks.Timer} and {@link com.stardevllc.clock.clocks.Stopwatch}
 * Note: It is planned to include a {@link IBuilder} implementation but there are a few things that need rewrites first.</pre>
 * @param <T> The type for the {@link ClockSnapshot} instance
 */
public abstract class Clock<T extends ClockSnapshot> {
    protected final UUIDProperty uniqueId;
    protected final StringProperty name;
    protected final ClockLongProperty time;
    protected final ClockBooleanProperty paused;
    protected final ClockBooleanProperty cancelled;
    protected Map<UUID, CallbackHolder<T>> callbacks = new LinkedHashMap<>();
    protected final long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    /**
     * Constructs a new Clock
     *
     * @param uuid        The unique id of this Clock
     * @param time        The starting time for this clock
     * @param countAmount The amount that counts by each time that a tick happens
     */
    public Clock(UUID uuid, long time, long countAmount) {
        this(uuid, "", time, countAmount);
    }
    
    /**
     * Constructs a new clock
     *
     * @param uuid        The unique id of this clock
     * @param name        The name of th is clock
     * @param time        The starting time for this clock
     * @param countAmount The amount that counts by each time that a tick happens
     */
    public Clock(UUID uuid, String name, long time, long countAmount) {
        this.uniqueId = new UUIDProperty(this, "uniqueid", uuid);
        this.name = new StringProperty(this, "name", name);
        this.time = new ClockLongProperty(this, "time", time);
        this.paused = new ClockBooleanProperty(this, "paused", true);
        this.cancelled = new ClockBooleanProperty(this, "cancelled", false);
        this.countAmount = countAmount;
        
        this.time.addListener(e -> unpause());
    }
    
    /**
     * <pre>This controls how clocks work, this is entirely dependant on how many times that it is called
     * A default implementation exists in {@link ClockRunnable} for running clocks</pre>
     */
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
    
    /**
     * The amount of milliseconds that is counted by each time a {@link Clock#tick()} occurs
     *
     * @return The amount in milliseconds
     */
    public final long getCountAmount() {
        return countAmount;
    }
    
    /**
     * Abstract method that is called when a {@link Clock#tick()} happens
     */
    protected abstract void count();
    
    /**
     * Creates a Snapshot of the current clock's values
     *
     * @return The new snapshot instance
     */
    public abstract T createSnapshot();
    
    /**
     * Method to see if a holder should run
     *
     * @param holder The holder
     * @return If it should run
     */
    protected abstract boolean shouldCallback(CallbackHolder<T> holder);
    
    /**
     * See {@link Clock#shouldCallback(CallbackHolder)} This method just retrieves the holder instance based onthe uuid
     *
     * @param callbackUUID The uuid of the callback
     * @return If it should run
     */
    public boolean shouldCallback(UUID callbackUUID) {
        CallbackHolder<T> callbackHolder = this.callbacks.get(callbackUUID);
        if (callbackHolder == null) {
            return false;
        }
        
        return shouldCallback(callbackHolder);
    }
    
    /**
     * Returns when the next run of the holder happens relative to the clock
     *
     * @param holder The holder
     * @return The next run in milliseconds relative to the clock
     */
    protected abstract long getNextRun(CallbackHolder<T> holder);
    
    /**
     * Se {@link Clock#getNextRun(CallbackHolder)} This method just gets the holder and calls that method
     *
     * @param callbackUUID The uuid of the callback
     * @return The time in milliseconds when the callback is ran relative to the clock
     */
    public long getNextRun(UUID callbackUUID) {
        CallbackHolder<T> callbackHolder = this.callbacks.get(callbackUUID);
        if (callbackHolder == null) {
            return 0;
        }
        
        return getNextRun(callbackHolder);
    }
    
    /**
     * Logic for handling callbacks. You really shouldn't call or override this method for most use cases unless you are making a runnable
     *
     * @param snapshot The snapshot of the callback
     */
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
    
    /**
     * Starts this clock
     *
     * @return This clock instance
     */
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    /**
     * Pauses this clock from running
     */
    public void pause() {
        this.paused.set(true);
    }
    
    /**
     * Unpauses this clock and will let it run starting on next tick
     */
    public void unpause() {
        this.paused.set(false);
    }
    
    /**
     * Cancels this clock
     */
    public void cancel() {
        this.cancelled.set(true);
    }
    
    /**
     * Uncancels this clock. Note: this is only effective if another tick has not happened between the cancel and uncancel methods
     */
    public void uncancel() {
        this.cancelled.set(false);
    }
    
    /**
     * @return The cancelled state
     */
    public boolean isCancelled() {
        return cancelled.get();
    }
    
    /**
     * @return The current time
     */
    public long getTime() {
        return time.get();
    }
    
    /**
     * @return The paused state
     */
    public boolean isPaused() {
        return paused.get();
    }
    
    /**
     * Adds time to the current time
     *
     * @param time The amount of time to add
     */
    public void addTime(long time) {
        this.time.setValue(this.time.get() + time);
    }
    
    /**
     * Removes time from this clock.
     *
     * @param time The amount of time to remove
     */
    public void removeTime(long time) {
        this.time.setValue(this.time.get() - time);
    }
    
    /**
     * Sets the time of this clock
     *
     * @param time The time to set it to
     */
    public void setTime(long time) {
        this.time.setValue(time);
    }
    
    /**
     * Adds a callback to this clock that runs at a specific time
     *
     * @param callback  The callback
     * @param runAtTime The time to run at relative to this clock
     * @return The unique id of the callback
     */
    public UUID addCallback(ClockCallback<T> callback, long runAtTime) {
        return addCallback(callback, () -> runAtTime, false);
    }
    
    /**
     * Adds a callback to this clock that runs at a specific time
     *
     * @param callback  The callback
     * @param runAtTime The time to run at relative to this clock
     * @return The unique id of the callback
     */
    public UUID addCallback(ClockCallback<T> callback, CallbackPeriod runAtTime) {
        return addCallback(callback, runAtTime, false);
    }
    
    /**
     * Adds a repeating callback to this clock
     *
     * @param callback The callback
     * @param period   How often to run
     * @return The unique id of the callback
     */
    public UUID addRepeatingCallback(ClockCallback<T> callback, long period) {
        return addCallback(callback, () -> period, true);
    }
    
    /**
     * Adds a repeating callback to this clock
     *
     * @param callback The callback
     * @param period   How often to run
     * @return The unique id of the callback
     */
    public UUID addRepeatingCallback(ClockCallback<T> callback, CallbackPeriod period) {
        return addCallback(callback, period, true);
    }
    
    /**
     * Adds a callback to this clock that runs at a specific time
     *
     * @param callback The callback
     * @param unit     The time unit
     * @param unitTime The amount of time in the unit
     * @return The uuid of the clock
     */
    public UUID addCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, () -> unit.toMillis(unitTime), false);
    }
    
    /**
     * Adds a repeating callback to this clock
     *
     * @param callback The callback
     * @param unit     The time unit
     * @param unitTime The amount of time in the unit
     * @return The uuid of the clock
     */
    public UUID addRepeatingCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, () -> unit.toMillis(unitTime), true);
    }
    
    /**
     * Adds a callback to this clock
     *
     * @param callback  The callback
     * @param period    The period to run
     * @param repeating If the callback repeats or not
     * @return The unique id of the callback
     */
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
    
    /**
     * <pre>Adds a repeating callback to this clock.
     * Note: The callback MUST implement the {@link ClockCallback#getPeriod()} method</pre>
     *
     * @param callback The callback to add
     * @return The unique id of the callback
     */
    public UUID addRepeatingCallback(ClockCallback<T> callback) {
        return addRepeatingCallback(callback, callback.getPeriod());
    }
    
    /**
     * <pre>Adds a callback that runs at a specific time to this clock
     * Note: The callback MUST implement the {@link ClockCallback#getPeriod()} method</pre>
     *
     * @param callback The callback to add
     * @return The unique id of the callback
     */
    public UUID addCallback(ClockCallback<T> callback) {
        return addCallback(callback, callback.getPeriod());
    }
    
    /**
     * Removes a callback from this clock
     *
     * @param uuid The uuid of the callback to remove
     */
    public void removeCallback(UUID uuid) {
        this.callbacks.remove(uuid);
    }
    
    /**
     * <pre>Sets the end condition of this clock
     * The end condition is just a way to automatically cancel this clock when it is true
     * Otherwise, without an end condition, the clock will still run if the time is modified
     * If the end condition is met, the clock becomes cancelled</pre>
     *
     * @param endCondition The end condition
     */
    public void setEndCondition(ClockEndCondition<T> endCondition) {
        this.endCondition = endCondition;
    }
    
    /**
     * @return The current end condition
     */
    public ClockEndCondition<T> getEndCondition() {
        return endCondition;
    }
    
    /**
     * Gets a callback instance
     *
     * @param uuid The uuid of the callback
     * @return The instance of the callback
     */
    public ClockCallback<T> getCallback(UUID uuid) {
        CallbackHolder<T> holder = this.callbacks.get(uuid);
        if (holder != null) {
            return holder.getCallback();
        }
        
        return null;
    }
    
    /**
     * @return The unique id of the clock
     */
    public UUID getUniqueId() {
        return this.uniqueId.get();
    }
    
    /**
     * @return The name of the clock
     */
    public String getName() {
        return this.name.get();
    }
    
    /**
     * Properties allow you to listen for changes directly. This allows direct access to those
     * @return The property instance
     */
    public LongProperty timeProperty() {
        return this.time;
    }
    
    /**
     * Properties allow you to listen for changes directly. This allows direct access to those
     * @return The property instance
     */
    public BooleanProperty pausedProperty() {
        return this.paused;
    }
    
    /**
     * Properties allow you to listen for changes directly. This allows direct access to those
     * @return The property instance
     */
    public BooleanProperty cancelledProperty() {
        return this.cancelled;
    }
    
    /**
     * Properties allow you to listen for changes directly. This allows direct access to those
     * @return The property instance
     */
    public UUIDProperty uuidProperty() {
        return this.uniqueId;
    }
    
    /**
     * Properties allow you to listen for changes directly. This allows direct access to those
     * @return The property instance
     */
    public StringProperty nameProperty() {
        return this.name;
    }
}