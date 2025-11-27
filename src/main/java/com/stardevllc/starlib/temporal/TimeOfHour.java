package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

public class TimeOfHour implements Temporal {

    protected final TimeValue timeValue;

    public static TimeOfHour timeOfHour(long minute, long second) {
        return new TimeOfHour(minute, second);
    }

    public TimeOfHour() {
        this.timeValue = new TimeValue();
    }

    public TimeOfHour(long minute, long second) {
        this();
        timeValue.add(TimeUnit.MINUTES, minute);
        timeValue.add(TimeUnit.SECONDS, second);
    }

    protected TimeOfHour(TimeValue timeValue) {
        this.timeValue = new TimeValue(timeValue.getYear(), timeValue.getTimeOfYear());
    }
    
    public TimeOfHour(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    @Override
    public TimeValue getTime() {
        return timeValue;
    }
    
    public boolean between(TimeOfHour lower, TimeOfHour upper) {
        return this.greaterThanOrEqualTo(lower) && this.lessThanOrEqualTo(upper);
    }

    @Override
    public TimeOfHour clone() {
        return new TimeOfHour(this.timeValue);
    }

    public long getMinute() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.HOURS.getMsPerUnit();
        return remaining / TimeUnit.MINUTES.getMsPerUnit();
    }

    public TimeOfHour addMinutes(long minutes) {
        return add(TimeUnit.MINUTES, minutes);
    }

    public TimeOfHour subtractMinutes(long minutes) {
        return subtract(TimeUnit.MINUTES, minutes);
    }

    public long getSecond() {
        return this.timeValue.getTimeOfYear() % TimeUnit.MINUTES.getMsPerUnit();
    }

    public TimeOfHour addSeconds(long seconds) {
        return add(seconds);
    }

    public TimeOfHour subtractSeconds(long seconds) {
        return subtract(seconds);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", getMinute(), getSecond());
    }

    @Override
    public TimeOfHour add(long milliseconds) {
        return (TimeOfHour) Temporal.super.add(milliseconds);
    }

    @Override
    public TimeOfHour add(TimeUnit unit, long amount) {
        return (TimeOfHour) Temporal.super.add(unit, amount);
    }

    @Override
    public TimeOfHour add(Temporal temporal, Temporal... temporals) {
        return (TimeOfHour) Temporal.super.add(temporal, temporals);
    }

    @Override
    public TimeOfHour subtract(long seconds) {
        return (TimeOfHour) Temporal.super.subtract(seconds);
    }

    @Override
    public TimeOfHour subtract(TimeUnit unit, long amount) {
        return (TimeOfHour) Temporal.super.subtract(unit, amount);
    }

    @Override
    public TimeOfHour subtract(Temporal temporal, Temporal... temporals) {
        return (TimeOfHour) Temporal.super.subtract(temporal, temporals);
    }

    @Override
    public TimeOfHour addYears(long years) {
        return (TimeOfHour) Temporal.super.addYears(years);
    }

    @Override
    public TimeOfHour subtractYears(long years) {
        return (TimeOfHour) Temporal.super.subtractYears(years);
    }
}
