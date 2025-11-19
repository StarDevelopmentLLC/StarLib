package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

public class MonthDay implements Temporal {

    protected final TimeValue timeValue = new TimeValue();

    public MonthDay() {
    }

    public MonthDay(long month, long day) {
        timeValue.add(TimeUnit.MONTHS, month - 1);
        timeValue.add(TimeUnit.DAYS, day - 1);
    }

    protected MonthDay(TimeValue timeValue) {
        this.timeValue.setYear(timeValue.getYear());
        this.timeValue.setTimeOfYear(timeValue.getTimeOfYear());
    }

    @Override
    public TimeValue getTime() {
        return timeValue;
    }

    @Override
    public MonthDay clone() {
        return new MonthDay(this.timeValue);
    }

    public long getMonth() {
        return this.timeValue.getTimeOfYear() / TimeUnit.MONTHS.getMsPerUnit() + 1;
    }

    public MonthDay addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }

    public MonthDay subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }

    public long getDay() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit() + 1;
    }

    public MonthDay addDays(long days) {
        return add(TimeUnit.DAYS, days);
    }

    public MonthDay subtractDays(long days) {
        return subtract(TimeUnit.DAYS, days);
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getMonth(), getDay());
    }

    @Override
    public MonthDay add(long milliseconds) {
        return (MonthDay) Temporal.super.add(milliseconds);
    }

    @Override
    public MonthDay add(TimeUnit unit, long amount) {
        return (MonthDay) Temporal.super.add(unit, amount);
    }

    @Override
    public MonthDay add(Temporal temporal, Temporal... temporals) {
        return (MonthDay) Temporal.super.add(temporal, temporals);
    }

    @Override
    public MonthDay subtract(long seconds) {
        return (MonthDay) Temporal.super.subtract(seconds);
    }

    @Override
    public MonthDay subtract(TimeUnit unit, long amount) {
        return (MonthDay) Temporal.super.subtract(unit, amount);
    }

    @Override
    public MonthDay subtract(Temporal temporal, Temporal... temporals) {
        return (MonthDay) Temporal.super.subtract(temporal, temporals);
    }

    @Override
    public MonthDay addYears(long years) {
        return (MonthDay) Temporal.super.addYears(years);
    }

    @Override
    public MonthDay subtractYears(long years) {
        return (MonthDay) Temporal.super.subtractYears(years);
    }
}
