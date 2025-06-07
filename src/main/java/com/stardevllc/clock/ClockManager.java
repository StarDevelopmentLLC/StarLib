package com.stardevllc.clock;

import com.stardevllc.clock.callback.ClockCallback;
import com.stardevllc.clock.clocks.Stopwatch;
import com.stardevllc.clock.clocks.Timer;
import com.stardevllc.clock.snapshot.*;

import java.util.*;
import java.util.logging.Logger;

public class ClockManager {
    protected final Map<UUID, Clock<? extends ClockSnapshot>> clocks = Collections.synchronizedMap(new HashMap<>());
    protected long countAmount;
    protected ClockRunnable runnable;
    protected Logger logger;
    
    public ClockManager(Logger logger, long countAmount) {
        this.runnable = new ClockRunnable(this);
        this.countAmount = countAmount;
        this.logger = logger;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public void addClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.put(clock.getUniqueId(), clock);
    }
    
    public void removeClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.remove(clock.getUniqueId(), clock);
    }
    
    @SafeVarargs
    public final Timer createTimer(long length, ClockCallback<? extends TimerSnapshot>... callbacks) {
        return createTimer("", length, callbacks);
    }
    
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
    
    @SafeVarargs
    public final Stopwatch createStopwatch(ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(0L, 0L, callbacks);
    }
    
    @SafeVarargs
    public final Stopwatch createStopwatch(String name, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(name, 0L, 0L, callbacks);
    }
    
    @SafeVarargs
    public final Stopwatch createStopwatch(long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(0L, endTime, callbacks);
    }
    
    @SafeVarargs
    public final Stopwatch createStopwatch(String name, long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch(name, 0L, endTime, callbacks);
    }
    
    @SafeVarargs
    public final Stopwatch createStopwatch(long startTime, long endTime, ClockCallback<? extends StopwatchSnapshot>... callbacks) {
        return createStopwatch("", startTime, endTime, callbacks);
    }
    
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
    
    public ClockRunnable getRunnable() {
        return runnable;
    }
    
    public List<Clock<? extends ClockSnapshot>> getClocks() {
        return new ArrayList<>(clocks.values());
    }
    
    public long getCountAmount() {
        return countAmount;
    }
    
    public void setCountAmount(long countAmount) {
        this.countAmount = countAmount;
    }
}