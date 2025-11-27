package com.stardevllc.starlib.temporal;

import com.stardevllc.starlib.time.TimeUnit;

import java.util.Map;

@SuppressWarnings({"ConstantValue", "SameParameterValue"})
public class Duration implements Temporal {
    public static Duration addOrClone(Duration base, Duration toAdd) {
        if (toAdd == null) {
            return add(base, toAdd);
        } else {
            return add(base, toAdd.clone());
        }
    }
    
    public static Duration add(Duration base, Duration toAdd) {
        if (base == null) {
            return toAdd;
        } else {
            return base.add(toAdd);
        }
    }
    
    public static Duration duration(Temporal temporal, Temporal... temporals) {
        return new Duration().add(temporal, temporals);
    }
    
    public static Duration duration(TimeUnit timeUnit, long time) {
        return new Duration().add(timeUnit, time);
    }
    
    public static Duration seconds(long seconds) {
        return new Duration().add(seconds);
    }
    
    public static Duration minutes(long minutes) {
        return new Duration().addMinutes(minutes);
    }
    
    public static Duration hours(long hours) {
        return new Duration().addHours(hours);
    }
    
    public static Duration days(long days) {
        return new Duration().addDays(days);
    }
    
    public static Duration months(long months) {
        return new Duration().addMonths(months);
    }
    
    public static Duration years(long years) {
        return new Duration().addYears(years);
    }
    
    public static Duration weeks(long weeks) {
        return days(weeks * 7);
    }
    
    protected final TimeValue timeValue;
    
    public Duration() {
        this.timeValue = new TimeValue();
    }
    
    protected Duration(TimeValue timeValue) {
        this.timeValue = new TimeValue(timeValue.getYear(), timeValue.getTimeOfYear());
    }
    
    public Duration(Map<String, Object> serialized) {
        this.timeValue = (TimeValue) serialized.get("timevalue");
    }
    
    @Override
    public TimeValue getTime() {
        return timeValue;
    }
    
    @Override
    public Duration clone() {
        return new Duration(this.timeValue);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Duration duration = (Duration) o;
        return timeValue.equals(duration.timeValue);
    }
    
    @Override
    public int hashCode() {
        return timeValue.hashCode();
    }
    
    @Override
    public Duration set(long year, long timeOfYear) {
        return (Duration) Temporal.super.set(year, timeOfYear);
    }
    
    @Override
    public Duration set(Temporal temporal) {
        return (Duration) Temporal.super.set(temporal);
    }
    
    public long getYears() {
        return this.timeValue.getYear();
    }
    
    public long getMonths() {
        return this.timeValue.getTimeOfYear() / TimeUnit.MONTHS.getMsPerUnit();
    }
    
    public Duration addMonths(long months) {
        return add(TimeUnit.MONTHS, months);
    }
    
    public Duration subtractMonths(long months) {
        return subtract(TimeUnit.MONTHS, months);
    }
    
    public long getDays() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MONTHS.getMsPerUnit();
        return remaining / TimeUnit.DAYS.getMsPerUnit();
    }
    
    public Duration addDays(long days) {
        return add(TimeUnit.DAYS, days);
    }
    
    public Duration subtractDays(long days) {
        return subtract(TimeUnit.DAYS, days);
    }
    
    public long getHours() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.DAYS.getMsPerUnit();
        return remaining / TimeUnit.HOURS.getMsPerUnit();
    }
    
    public Duration addHours(long hours) {
        return add(TimeUnit.HOURS, hours);
    }
    
    public Duration subtractHours(long hours) {
        return subtract(TimeUnit.HOURS, hours);
    }
    
    public long getMinutes() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.HOURS.getMsPerUnit();
        return remaining / TimeUnit.MINUTES.getMsPerUnit();
    }
    
    public Duration addMinutes(long minutes) {
        return add(TimeUnit.MINUTES, minutes);
    }
    
    public Duration subtractMinutes(long minutes) {
        return subtract(TimeUnit.MINUTES, minutes);
    }
    
    public long getSeconds() {
        long remaining = this.timeValue.getTimeOfYear() % TimeUnit.MINUTES.getMsPerUnit();
        return remaining / TimeUnit.SECONDS.getMsPerUnit();
    }
    
    public Duration addSeconds(long seconds) {
        return add(TimeUnit.SECONDS, seconds);
    }
    
    public Duration subtractSeconds(long seconds) {
        return subtract(TimeUnit.SECONDS, seconds);
    }
    
    public long getMilliseconds() {
        return this.timeValue.getTimeOfYear() % TimeUnit.MILLISECONDS.getMsPerUnit();
    }
    
    public Duration addMilliseconds(long milliseconds) {
        return add(milliseconds);
    }
    
    public Duration subtractMilliseconds(long milliseconds) {
        return subtract(milliseconds);
    }
    
    public Duration divide(long divisor) {
        return new Duration(timeValue.divide(divisor));
    }
    
    public Duration divide(Duration divisor) {
        return new Duration(timeValue.divide(divisor.timeValue));
    }
    
    public Duration multiply(double factor) {
        return new Duration(timeValue.multiply(factor));
    }
    
    public String toStringShort() {
        StringBuilder sb = new StringBuilder();
        toStringAppend(sb, getYears(), "y ");
        toStringAppend(sb, getMonths(), "mo ");
        toStringAppend(sb, getDays(), "d ");
        toStringAppend(sb, getHours(), "h ");
        toStringAppend(sb, getMinutes(), "m ");
        toStringAppend(sb, getSeconds(), "s");
        return sb.toString();
    }
    
    public String toStringLong() {
        StringBuilder sb = new StringBuilder();
        long years = getYears();
        if (years > 0) {
            sb.append(years).append(" year");
            pluralize(years, sb);
        }
        
        long months = getMonths();
        if (months > 0) {
            sb.append(months).append(" month");
            pluralize(months, sb);
        }
        
        long days = getDays();
        if (days > 0) {
            sb.append(days).append(" day");
            pluralize(days, sb);
        }
        
        long hours = getHours();
        if (hours > 0) {
            sb.append(hours).append(" hour");
            pluralize(hours, sb);
        }
        
        long minutes = getMinutes();
        if (minutes > 0) {
            sb.append(minutes).append(" minute");
            pluralize(minutes, sb);
        }
        
        long seconds = getSeconds();
        if (seconds > 0) {
            sb.append(seconds).append(" second");
            pluralize(seconds, sb);
        }
        
        return sb.toString();
    }
    
    private void pluralize(long value, StringBuilder sb) {
        if (value > 1) {
            sb.append("s ");
        } else {
            sb.append(" ");
        }
    }
    
    private void toStringAppend(StringBuilder sb, long value, String unit) {
        toStringAppend(sb, value, unit, false);
    }
    
    private void toStringAppend(StringBuilder sb, long value, String unit, boolean pluralize) {
        if (value > 0) {
            sb.append(value).append(unit);
            if (pluralize) {
                pluralize(value, sb);
            }
        }
    }
    
    @Override
    public String toString() {
        return toStringLong();
    }
    
    @Override
    public Duration add(long milliseconds) {
        return (Duration) Temporal.super.add(milliseconds);
    }
    
    @Override
    public Duration add(TimeUnit unit, long amount) {
        return (Duration) Temporal.super.add(unit, amount);
    }
    
    @Override
    public Duration add(Temporal temporal, Temporal... temporals) {
        return (Duration) Temporal.super.add(temporal, temporals);
    }
    
    @Override
    public Duration subtract(long seconds) {
        return (Duration) Temporal.super.subtract(seconds);
    }
    
    @Override
    public Duration subtract(TimeUnit unit, long amount) {
        return (Duration) Temporal.super.subtract(unit, amount);
    }
    
    @Override
    public Duration subtract(Temporal temporal, Temporal... temporals) {
        return (Duration) Temporal.super.subtract(temporal, temporals);
    }
    
    @Override
    public Duration addYears(long years) {
        return (Duration) Temporal.super.addYears(years);
    }
    
    @Override
    public Duration subtractYears(long years) {
        return (Duration) Temporal.super.subtractYears(years);
    }
}
