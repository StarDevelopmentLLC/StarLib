package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

public class Instant implements Temporal {

    protected final TimeValue timeValue;

    public Instant() {
        this.timeValue = new TimeValue();
    }

    public Instant(Temporal temporal, Temporal... temporals) {
        this();
        add(temporal, temporals);
    }

    public Instant(long month, long day, long year, long hour, long minute, long second) {
        this();
        timeValue.setYear(year);
        timeValue.add(TimeUnit.MONTHS, month - 1);
        timeValue.add(TimeUnit.DAYS, day - 1);
        timeValue.add(TimeUnit.HOURS, hour);
        timeValue.add(TimeUnit.MINUTES, minute);
        timeValue.add(TimeUnit.SECONDS, second);
    }

    protected Instant(TimeValue timeValue) {
        this.timeValue = new TimeValue(timeValue.getYear(), timeValue.getTimeOfYear());
    }
    
    public Instant(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    public Duration distance(Instant other) {
        Instant distance = this.clone().subtract(other);
        return new Duration().set(distance);
    }

    @Override
    public TimeValue getTime() {
        return timeValue;
    }

    public Date getDate() {
        return new Date(getYear(), getMonth(), getDay());
    }

    public TimeOfDay getTimeOfDay() {
        return new TimeOfDay(getHour(), getMinute(), getSecond());
    }

    @Override
    public Instant clone() {
        return new Instant(this.timeValue);
    }

    public long getYear() {
        return this.timeValue.getYear();
    }

    public long getMonth() {
        return this.timeValue.getTimeOfYear() / TimeUnit.MONTHS.getMsPerUnit() + 1;
    }

    public Instant addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }

    public Instant subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }

    public long getDay() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit() + 1;
    }

    public Instant addDays(long days) {
        return add(TimeUnit.DAYS, days);
    }

    public Instant subtractDays(long days) {
        return subtract(TimeUnit.DAYS, days);
    }

    public long getHour() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.DAYS.getMsPerUnit();
        return remaining / TimeUnit.HOURS.getMsPerUnit();
    }

    public Instant addHours(long hours) {
        return add(TimeUnit.HOURS, hours);
    }

    public Instant subtractHours(long hours) {
        return subtract(TimeUnit.HOURS, hours);
    }

    public long getMinute() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.HOURS.getMsPerUnit();
        return remaining / TimeUnit.MINUTES.getMsPerUnit();
    }

    public Instant addMinutes(long minutes) {
        return add(TimeUnit.MINUTES, minutes);
    }

    public Instant subtractMinutes(long minutes) {
        return subtract(TimeUnit.MINUTES, minutes);
    }

    public long getSecond() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MINUTES.getMsPerUnit();
        return remaining / TimeUnit.SECONDS.getMsPerUnit();
    }

    public Instant addSeconds(long seconds) {
        return add(TimeUnit.SECONDS, seconds);
    }

    public Instant subtractSeconds(long seconds) {
        return subtract(TimeUnit.SECONDS, seconds);
    }
    
    public long getMillisecond() {
        return this.timeValue.getTimeOfYear() / TimeUnit.MILLISECONDS.getMsPerUnit();
    }
    
    public Instant addMilliseconds(long milliseconds) {
        return add(milliseconds);
    }
    
    public Instant subtractMilliseconds(long milliseconds) {
        return subtract(milliseconds);
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s %s:%s:%s", format.format(getMonth()), format.format(getDay()), format.format(getYear()), format.format(getHour()), format.format(getMinute()), format.format(getSecond()));
    }

    @Override
    public Instant add(long milliseconds) {
        return (Instant) Temporal.super.add(milliseconds);
    }

    @Override
    public Instant add(TimeUnit unit, long amount) {
        return (Instant) Temporal.super.add(unit, amount);
    }

    @Override
    public Instant add(Temporal temporal, Temporal... temporals) {
        return (Instant) Temporal.super.add(temporal, temporals);
    }

    @Override
    public Instant subtract(long seconds) {
        return (Instant) Temporal.super.subtract(seconds);
    }

    @Override
    public Instant subtract(TimeUnit unit, long amount) {
        return (Instant) Temporal.super.subtract(unit, amount);
    }

    @Override
    public Instant subtract(Temporal temporal, Temporal... temporals) {
        return (Instant) Temporal.super.subtract(temporal, temporals);
    }

    @Override
    public Instant addYears(long years) {
        return (Instant) Temporal.super.addYears(years);
    }

    @Override
    public Instant subtractYears(long years) {
        return (Instant) Temporal.super.subtractYears(years);
    }
}