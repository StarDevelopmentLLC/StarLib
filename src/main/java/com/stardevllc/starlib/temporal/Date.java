package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

public class Date implements Temporal {

    protected final TimeValue timeValue;

    public Date() {
        this.timeValue = new TimeValue();
    }

    public Date(long year, long month, long day) {
        this.timeValue = new TimeValue();
        timeValue.setYear(year);
        timeValue.add(TimeUnit.MONTHS, month - 1);
        timeValue.add(TimeUnit.DAYS, day - 1);
    }

    protected Date(TimeValue timeValue) {
        this.timeValue = new TimeValue();
        this.timeValue.setYear(timeValue.getYear());
        this.timeValue.setTimeOfYear(timeValue.getTimeOfYear());
    }
    
    public Date(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    @Override
    public TimeValue getTime() {
        return timeValue;
    }

    public Instant at(TimeOfDay timeOfDay) {
        return new Instant(this, timeOfDay);
    }

    @Override
    public Date clone() {
        return new Date(this.timeValue);
    }

    public long getYear() {
        return this.timeValue.getYear();
    }

    public long getMonth() {
        return this.timeValue.getTimeOfYear() / TimeUnit.MONTHS.getMsPerUnit() + 1;
    }

    public Date addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }

    public Date subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }

    public long getDay() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit() + 1;
    }

    public Date addDays(long days) {
        return add(TimeUnit.DAYS, days);
    }

    public Date subtractDays(long days) {
        return subtract(TimeUnit.DAYS, days);
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s", getMonth(), getDay(), getYear());
    }

    @Override
    public Date add(long milliseconds) {
        return (Date) Temporal.super.add(milliseconds);
    }

    @Override
    public Date add(TimeUnit unit, long amount) {
        return (Date) Temporal.super.add(unit, amount);
    }

    @Override
    public Date add(Temporal temporal, Temporal... temporals) {
        return (Date) Temporal.super.add(temporal, temporals);
    }

    @Override
    public Date subtract(long milliseconds) {
        return (Date) Temporal.super.subtract(milliseconds);
    }

    @Override
    public Date subtract(TimeUnit unit, long amount) {
        return (Date) Temporal.super.subtract(unit, amount);
    }

    @Override
    public Date subtract(Temporal temporal, Temporal... temporals) {
        return (Date) Temporal.super.subtract(temporal, temporals);
    }

    @Override
    public Date addYears(long years) {
        return (Date) Temporal.super.addYears(years);
    }

    @Override
    public Date subtractYears(long years) {
        return (Date) Temporal.super.subtractYears(years);
    }
}
