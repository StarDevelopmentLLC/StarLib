package com.stardevllc.starlib.time;

/**
 * An enum utility to make working with times easier. 
 * Yes, there is a Java class for this, but This was to make it easier to parse times in a Minecraft context, plus the java TimeUnit does not have weeks, months or years
 */
public enum TimeUnit {
    MILLISECONDS(1, "millisecond", "ms"),
    TICKS(50, "tick", "t"),
    SECONDS(1000, "second", "s") ,
    MINUTES(SECONDS.msPerUnit * 60, "minute", "min", "m"),
    HOURS(MINUTES.msPerUnit * 60, "hour", "h"),
    DAYS(HOURS.msPerUnit * 24, "day", "d"),
    /**
     * The {@code WEEKS} value is based on a 7.604166666666667-day week. This is the average weeks in a month based on the below month value
     */
    WEEKS((long) (DAYS.msPerUnit * 7.604166666666667), "week", "w"),
    /**
     * The {@code MONTHS} value is based on a 30.41666667-day month. This is the average days in a month based on a 365 day year
     */
    MONTHS((long) (DAYS.msPerUnit * 30.41666667), "month", "mo"),
    /**
     * The {@code YEARS} value is based on a 365-day year. If another day count is needed, convert from the {@code DAYS} enum value.
     */
    YEARS(DAYS.msPerUnit * 365, "year", "y");
    
    private final String name;
    private final String[] aliases;
    private final long msPerUnit;

    TimeUnit(long msPerUnit, String... aliases) {
        this.msPerUnit = msPerUnit;
        this.name = name().toLowerCase();
        this.aliases = aliases;
    }

    /**
     * @return The primary name of the TimeUnit. This is taken from the lowercase name() value
     */
    public String getName() {
        return name;
    }

    /**
     * @return All aliases of the TimeUnit. This can be null
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Converts the time from milliseconds to this unit. Note Precision is lost
     * @param milliseconds The time in milliseconds
     * @return The amount of time represented by this unit
     */
    public long fromMillis(long milliseconds) {
        return milliseconds / this.msPerUnit;
    }

    /**
     * Converts the amount to the millisecond equivalent
     * @param duration The duration to convert in this Unit
     * @return The time in milliseconds
     */
    public long toMillis(long duration) {
        return msPerUnit * duration;
    }

    /**
     * Converts the amount to the ticks equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in Ticks
     */
    public long toTicks(long duration) {
        return toMillis(duration) / TICKS.toMillis(1);
    }

    /**
     * Converts the amount to the seconds equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in seconds
     */
    public long toSeconds(long duration) {
        return toMillis(duration) / SECONDS.toMillis(1);
    }

    /**
     * Converts the amount to the minutes equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in minutes
     */
    public long toMinutes(long duration) {
        return toMillis(duration) / MINUTES.toMillis(1);
    }

    /**
     * Converts the amount to the hours equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in hours
     */
    public long toHours(long duration) {
        return toMillis(duration) / HOURS.toMillis(1);
    }

    /**
     * Converts the amount to the days equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in days
     */
    public long toDays(long duration) {
        return toMillis(duration) / DAYS.toMillis(1);
    }

    /**
     * Converts the amount to the weeks equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in weeks
     */
    public long toWeeks(long duration) {
        return toMillis(duration) / WEEKS.toMillis(1);
    }

    /**
     * Converts the amount to the months equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in months
     */
    public long toMonths(long duration) {
        return toMillis(duration) / MONTHS.toMillis(1);
    }

    /**
     * Converts the amount to the years equivalent, Note: Converting from lower units to higher units will loose precision
     * @param duration The duration to convert in this Unit
     * @return The time in years
     */
    public long toYears(long duration) {
        return toMillis(duration) / YEARS.toMillis(1);
    }

    /**
     * @return The amount of milliseconds per single unit that this TimeUnit represents.
     */
    public long getMsPerUnit() {
        return msPerUnit;
    }

    /**
     * This matches the unit based on the name and the aliases
     * @param unitString The string to match
     * @return The unit that matches or null if it does not
     */
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