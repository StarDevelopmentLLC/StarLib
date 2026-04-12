package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
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
        timeValue.add(TimeUnit.YEARS.getMsPerUnit() * year);
        timeValue.add(TimeUnit.MONTHS.getMsPerUnit() * month);
        timeValue.add(TimeUnit.DAYS.getMsPerUnit() * day);
        timeValue.add(TimeUnit.HOURS.getMsPerUnit() * hour);
        timeValue.add(TimeUnit.MINUTES.getMsPerUnit() * minute);
        timeValue.add(TimeUnit.SECONDS.getMsPerUnit() * second);
    }

    protected Instant(TimeValue timeValue) {
        this.timeValue = timeValue.clone();
    }
    
    public Instant(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }

    public Duration distance(Instant other) {
        Instant distance = this.clone().subtract(other);
        return new Duration().set(distance);
    }

    @Override
    public TimeValue getTimeValue() {
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
        return this.timeValue.getTime() / TimeUnit.YEARS.getMsPerUnit();
    }

    public long getMonth() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        return remaining / TimeUnit.MONTHS.getMsPerUnit();
    }

    public Instant addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }

    public Instant subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }

    public long getDay() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit();
    }

    public Instant addDays(long days) {
        return add(TimeUnit.DAYS, days);
    }

    public Instant subtractDays(long days) {
        return subtract(TimeUnit.DAYS, days);
    }

    public long getHour() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        remaining = remaining % TimeUnit.DAYS.getMsPerUnit();
        return remaining / TimeUnit.HOURS.getMsPerUnit();
    }

    public Instant addHours(long hours) {
        return add(TimeUnit.HOURS, hours);
    }

    public Instant subtractHours(long hours) {
        return subtract(TimeUnit.HOURS, hours);
    }

    public long getMinute() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        remaining = remaining % TimeUnit.DAYS.getMsPerUnit();
        remaining = remaining % TimeUnit.HOURS.getMsPerUnit();
        return remaining / TimeUnit.MINUTES.getMsPerUnit();
    }

    public Instant addMinutes(long minutes) {
        return add(TimeUnit.MINUTES, minutes);
    }

    public Instant subtractMinutes(long minutes) {
        return subtract(TimeUnit.MINUTES, minutes);
    }

    public long getSecond() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        remaining = remaining % TimeUnit.DAYS.getMsPerUnit();
        remaining = remaining % TimeUnit.HOURS.getMsPerUnit();
        remaining = remaining % TimeUnit.MINUTES.getMsPerUnit();
        return remaining / TimeUnit.SECONDS.getMsPerUnit();
    }

    public Instant addSeconds(long seconds) {
        return add(TimeUnit.SECONDS, seconds);
    }

    public Instant subtractSeconds(long seconds) {
        return subtract(TimeUnit.SECONDS, seconds);
    }
    
    public long getMillisecond() {
        long remaining = this.timeValue.getTime() % TimeUnit.YEARS.getMsPerUnit();
        remaining = remaining % TimeUnit.MONTHS.getMsPerUnit();
        remaining = remaining % TimeUnit.DAYS.getMsPerUnit();
        remaining = remaining % TimeUnit.HOURS.getMsPerUnit();
        remaining = remaining % TimeUnit.MINUTES.getMsPerUnit();
        remaining = remaining % TimeUnit.SECONDS.getMsPerUnit();
        return remaining / TimeUnit.SECONDS.getMsPerUnit();
    }
    
    public Instant addMilliseconds(long milliseconds) {
        return add(milliseconds);
    }
    
    public Instant subtractMilliseconds(long milliseconds) {
        return subtract(milliseconds);
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s %s:%s:%s", format.format(getMonth()), format.format(getDay()), getYear(), format.format(getHour()), format.format(getMinute()), format.format(getSecond()));
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

    public Instant addYears(long years) {
        return add(TimeUnit.YEARS, years);
    }

    public Instant subtractYears(long years) {
        return subtract(TimeUnit.YEARS, years);
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Instant instant)) {
            return false;
        }
        
        return Objects.equals(timeValue, instant.timeValue);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(timeValue);
    }
}