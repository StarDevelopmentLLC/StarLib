package com.stardevllc.starlib.clock;

import com.stardevllc.starlib.clock.callback.ClockCallback;
import com.stardevllc.starlib.clock.clocks.Stopwatch;
import com.stardevllc.starlib.clock.clocks.Timer;
import com.stardevllc.starlib.clock.snapshot.*;

import java.util.*;

/**
 * <pre>This is an overall manager that provides some default implementations to prevent making this yourself
 * You don't have to use this if you don't want to, but you will have to manage everying that this manages</pre>
 */
public class ClockManager {
    private final Map<UUID, Clock<? extends ClockSnapshot>> clocks = Collections.synchronizedMap(new HashMap<>());
    private long countAmount;
    private ClockRunnable runnable;
    
    /**
     * Constructs a new clock manager
     *
     * @param countAmount The amount in milliseconds that clocks count at
     */
    public ClockManager(long countAmount) {
        this.runnable = new ClockRunnable(this);
        this.countAmount = countAmount;
    }
    
    /**
     * Adds a clock
     *
     * @param clock The clock
     */
    public void addClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.put(clock.getUniqueId(), clock);
    }
    
    /**
     * Removes a clock
     *
     * @param clock The clock
     */
    public void removeClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.remove(clock.getUniqueId(), clock);
    }
    
    /**
     * Creates a timer
     *
     * @param length    The length of the timer
     * @param callbacks The callbacks for the timer
     * @return The new timer
     */
    @SafeVarargs
    public final Timer createTimer(long length, ClockCallback<? extends TimerSnapshot>... callbacks) {
        return createTimer("", length, callbacks);
    }
    
    /**
     * Creates a timer
     *
     * @param name      The name of the timer
     * @param length    The length of the timer
     * @param callbacks The callbacks of the timer
     * @return The new timer
     */
    @SafeVarargs
    public final Timer createTimer(String name, long length, ClockCallback<? extends TimerSnapshot>... callbacks) {
        Timer timer = new Timer(UUID.randomUUID(), name, length, countAmount);
        addClock(timer);
        
        if (callbacks != null) {
            for (ClockCallback<? extends TimerSnapshot> callback : callbacks) {
                timer.addCallback((ClockCallback<TimerSnapshot>) callback);
            }
        }
        return timer;
    }
    
    /**
     * Creates a stopwatch
     *
     * @param callbacks The callbacks for the stopwatch
     * @return The new stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(0L, 0L, callbacks);
    }
    
    /**
     * Creates a stopwatch
     *
     * @param name      The name of the stopwatch
     * @param callbacks The callbacks of the stopwatch
     * @return The new stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(String name, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(name, 0L, 0L, callbacks);
    }
    
    /**
     * Creates a stopwatch
     *
     * @param endTime   The end time of the stopwatch
     * @param callbacks The callbacks of the stopwatch
     * @return The new stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(0L, endTime, callbacks);
    }
    
    /**
     * Creates a stopwatch
     *
     * @param name      The name of the stopwatch
     * @param endTime   The end time of the stopwatch
     * @param callbacks The callbacks of the stopwatch
     * @return The stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(String name, long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(name, 0L, endTime, callbacks);
    }
    
    /**
     * Creates a stopwatch
     *
     * @param startTime The start time of the stopwatch
     * @param endTime   The end time of the stopwatch
     * @param callbacks The callbacks of the stopwatch
     * @return The stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(long startTime, long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch("", startTime, endTime, callbacks);
    }
    
    /**
     * Creates a stopwatch
     *
     * @param name      The name of the stopwatch
     * @param startTime The start time of the stopwatch
     * @param endTime   The end time of the stopwatch
     * @param callbacks The callbacks of the stopwatch
     * @return The stopwatch
     */
    @SafeVarargs
    public final Stopwatch createStopwatch(String name, long startTime, long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        Stopwatch stopwatch = new Stopwatch(UUID.randomUUID(), name, startTime, endTime, countAmount);
        addClock(stopwatch);
        
        if (callbacks != null) {
            for (ClockCallback<? extends StopwatchSnapshot> callback : callbacks) {
                stopwatch.addCallback((ClockCallback<StopwatchSnapshot>) callback);
            }
        }
        
        return stopwatch;
    }
    
    /**
     * The clock runnable
     *
     * @return The clock runnable
     */
    public ClockRunnable getRunnable() {
        return runnable;
    }
    
    /**
     * A copy of the clocks list
     *
     * @return The copy of the clocks list
     */
    public List<Clock<? extends ClockSnapshot>> getClocks() {
        return new ArrayList<>(clocks.values());
    }
    
    /**
     * The count amount
     *
     * @return The count amount
     */
    public long getCountAmount() {
        return countAmount;
    }
    
    /**
     * Sets the count amount
     *
     * @param countAmount The new count about
     */
    public void setCountAmount(long countAmount) {
        this.countAmount = countAmount;
    }
}