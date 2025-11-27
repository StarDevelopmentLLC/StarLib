package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

public class TimeOfDay implements Temporal {

    protected final TimeValue timeValue;

    public static TimeOfDay timeOfDay(long hour, long minute, long second) {
        return new TimeOfDay(hour, minute, second);
    }

    public TimeOfDay() {
        this.timeValue = new TimeValue();
    }

    public TimeOfDay(long hour, long minute, long second) {
        this();
        timeValue.add(TimeUnit.HOURS, hour);
        timeValue.add(TimeUnit.MINUTES, minute);
        timeValue.add(TimeUnit.SECONDS, second);
    }

    protected TimeOfDay(TimeValue timeValue) {
        this.timeValue = new TimeValue(timeValue.getYear(), timeValue.getTimeOfYear());
    }
    
    public TimeOfDay(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    @Override
    public TimeValue getTime() {
        return timeValue;
    }
    
    public boolean between(TimeOfDay lower, TimeOfDay upper) {
        return this.greaterThanOrEqualTo(lower) && this.lessThanOrEqualTo(upper);
    }

    @Override
    public TimeOfDay clone() {
        return new TimeOfDay(this.timeValue);
    }

    public long getHour() {
        return this.timeValue.getTimeOfYear() / TimeUnit.HOURS.getMsPerUnit();
    }

    public TimeOfDay addHours(long hours) {
        return add(TimeUnit.HOURS, hours);
    }

    public TimeOfDay subtractHours(long hours) {
        return subtract(TimeUnit.HOURS, hours);
    }

    public long getMinute() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.HOURS.getMsPerUnit();
        return remaining / TimeUnit.MINUTES.getMsPerUnit();
    }

    public TimeOfDay addMinutes(long minutes) {
        return add(TimeUnit.MINUTES, minutes);
    }

    public TimeOfDay subtractMinutes(long minutes) {
        return subtract(TimeUnit.MINUTES, minutes);
    }

    public long getSecond() {
        return this.timeValue.getTimeOfYear() % TimeUnit.MINUTES.getMsPerUnit();
    }

    public TimeOfDay addSeconds(long seconds) {
        return add(seconds);
    }

    public TimeOfDay subtractSeconds(long seconds) {
        return subtract(seconds);
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", getHour(), getMinute(), getSecond());
    }

    @Override
    public TimeOfDay add(long milliseconds) {
        return (TimeOfDay) Temporal.super.add(milliseconds);
    }

    @Override
    public TimeOfDay add(TimeUnit unit, long amount) {
        return (TimeOfDay) Temporal.super.add(unit, amount);
    }

    @Override
    public TimeOfDay add(Temporal temporal, Temporal... temporals) {
        return (TimeOfDay) Temporal.super.add(temporal, temporals);
    }

    @Override
    public TimeOfDay subtract(long seconds) {
        return (TimeOfDay) Temporal.super.subtract(seconds);
    }

    @Override
    public TimeOfDay subtract(TimeUnit unit, long amount) {
        return (TimeOfDay) Temporal.super.subtract(unit, amount);
    }

    @Override
    public TimeOfDay subtract(Temporal temporal, Temporal... temporals) {
        return (TimeOfDay) Temporal.super.subtract(temporal, temporals);
    }

    @Override
    public TimeOfDay addYears(long years) {
        return (TimeOfDay) Temporal.super.addYears(years);
    }

    @Override
    public TimeOfDay subtractYears(long years) {
        return (TimeOfDay) Temporal.super.subtractYears(years);
    }
}