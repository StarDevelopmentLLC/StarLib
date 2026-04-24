package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

public class Date implements Temporal {

    protected final TimeValue timeValue;

    public Date() {
        this.timeValue = new TimeValue();
    }
    
    public Date(long millis) {
        this();
        this.timeValue.set(millis);
    }
    
    public Date(Temporal temporal, Temporal... temporals) {
        this();
        this.timeValue.add(temporal.getTimeValue());
        if (temporals != null) {
            for (Temporal t : temporals) {
                this.timeValue.add(temporal.getTimeValue());
            }
        }
    }

    public Date(long year, long month, long day) {
        this();
        timeValue.add(TimeUnit.YEARS.getMsPerUnit() * year);
        timeValue.add(TimeUnit.MONTHS.getMsPerUnit() * month);
        timeValue.add(TimeUnit.DAYS.getMsPerUnit() * day);
    }

    protected Date(TimeValue timeValue) {
        this();
        this.timeValue.set(timeValue.getTime());
    }
    
    public Date(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    @Override
    public TimeValue getTimeValue() {
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
        return this.timeValue.getTime() / TimeUnit.YEARS.getMsPerUnit();
    }
    
    public Date addYears(long years) {
        return add(TimeUnit.YEARS, years);
    }
    
    public long getMonth() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        return remaining / TimeUnit.MONTHS.getMsPerUnit();
    }

    public Date addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }

    public Date subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }
    
    public long getDay() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit();
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
}
