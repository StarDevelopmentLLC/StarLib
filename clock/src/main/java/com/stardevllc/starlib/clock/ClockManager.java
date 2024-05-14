package com.stardevllc.starlib.clock;

import com.stardevllc.starlib.clock.clocks.Stopwatch;
import com.stardevllc.starlib.clock.clocks.Timer;
import com.stardevllc.starlib.clock.snapshot.ClockSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ClockManager {
    protected final List<Clock<? extends ClockSnapshot>> clocks = Collections.synchronizedList(new ArrayList<>());
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
        this.clocks.add(clock);
    }
    
    public void removeClock(Clock<? extends ClockSnapshot> clock) {
        this.clocks.remove(clock);
    }
    
    public Timer createTimer(long length) {
        Timer timer = new Timer(length, countAmount);
        addClock(timer);
        return timer;
    }
    
    public Stopwatch createStopwatch(long endTime) {
        Stopwatch stopwatch = new Stopwatch(endTime, countAmount);
        addClock(stopwatch);
        return stopwatch;
    }
    
    public ClockRunnable getRunnable() {
        return runnable;
    }
    
    public List<Clock<? extends ClockSnapshot>> getClocks() {
        return clocks;
    }
    
    public long getCountAmount() {
        return countAmount;
    }
    
    public void setCountAmount(long countAmount) {
        this.countAmount = countAmount;
    }
}