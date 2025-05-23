package com.stardevllc.time;

import com.stardevllc.converter.string.*;

public enum TimeUnit {
    MILLISECONDS(1, "millisecond", "ms"),
    TICKS(50, "tick", "t"),
    SECONDS(java.util.concurrent.TimeUnit.SECONDS.toMillis(1), "second", "s") ,
    MINUTES(java.util.concurrent.TimeUnit.MINUTES.toMillis(1), "minute", "min", "m"),
    HOURS(java.util.concurrent.TimeUnit.HOURS.toMillis(1), "hour", "h"),
    DAYS(java.util.concurrent.TimeUnit.DAYS.toMillis(1), "day", "d"),
    WEEKS(DAYS.msPerUnit * 7, "week", "w"),
    MONTHS(DAYS.msPerUnit * 30, "month", "mo"),
    YEARS(DAYS.msPerUnit * 365, "year", "y");
    
    static {
        StringConverters.addConverter(TimeUnit.class, new EnumStringConverter<>(TimeUnit.class));
    }
    
    private final String name;
    private final String[] aliases;
    private final long msPerUnit;

    TimeUnit(long msPerUnit, String... aliases) {
        this.msPerUnit = msPerUnit;
        this.name = name().toLowerCase();
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public double fromMillis(long milliseconds) {
        return milliseconds / (this.msPerUnit * 1.0);
    }

    public long toMillis(long duration) {
        return msPerUnit * duration;
    }

    public double toTicks(long duration) {
        return toMillis(duration) / (TICKS.toMillis(1) * 1.0);
    }

    public double toSeconds(long duration) {
        return toMillis(duration) / (SECONDS.toMillis(1) * 1.0);
    }

    public double toMinutes(long duration) {
        return toMillis(duration) / (MINUTES.toMillis(1) * 1.0);
    }

    public double toHours(long duration) {
        return toMillis(duration) / (HOURS.toMillis(1) * 1.0);
    }

    public double toDays(long duration) {
        return toMillis(duration) / (DAYS.toMillis(1) * 1.0);
    }

    public double toWeeks(long duration) {
        return toMillis(duration) / (WEEKS.toMillis(1) * 1.0);
    }

    public double toMonths(long duration) {
        return toMillis(duration) / (MONTHS.toMillis(1) * 1.0);
    }

    public double toYears(long duration) {
        return toMillis(duration) / (YEARS.toMillis(1) * 1.0);
    }

    public long getMsPerUnit() {
        return msPerUnit;
    }

    public static TimeUnit matchUnit(String unitString) {
        for (TimeUnit unit : values()) {
            if (unit.getName().equalsIgnoreCase(unitString)) {
                return unit;
            }

            for (String alias : unit.getAliases()) {
                if (alias.equalsIgnoreCase(unitString)) {
                    return unit;
                }
            }
        }

        return null;
    }
}