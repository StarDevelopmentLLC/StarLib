package com.stardevllc.starlib.time;

import com.stardevllc.starlib.converter.string.EnumStringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

/**
 * A utility for time units that uses the same values as {@link java.util.concurrent.TimeUnit} for the same values but extends upon it and has aliases
 */
public enum TimeUnit {
    /**
     * Milliseconds (base unit)
     */
    MILLISECONDS(1, "millisecond", "ms"),
    
    /**
     * Ticks, Minecraft, 50ms
     */
    TICKS(50, "tick", "t"),
    
    /**
     * Seconds
     */
    SECONDS(java.util.concurrent.TimeUnit.SECONDS.toMillis(1), "second", "s"),
    
    /**
     * Minutes
     */
    MINUTES(java.util.concurrent.TimeUnit.MINUTES.toMillis(1), "minute", "min", "m"),
    
    /**
     * Hours
     */
    HOURS(java.util.concurrent.TimeUnit.HOURS.toMillis(1), "hour", "h"),
    
    /**
     * Days
     */
    DAYS(java.util.concurrent.TimeUnit.DAYS.toMillis(1), "day", "d"),
    
    /**
     * Weeks
     */
    WEEKS(DAYS.msPerUnit * 7, "week", "w"),
    
    /**
     * Months (30 days)
     */
    MONTHS((long) (DAYS.msPerUnit * 30.416666666667), "month", "mo"),
    
    /**
     * Years (365 days)
     */
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
    
    /**
     * Gets the  name of the unit as a single
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the aliases of the unit
     *
     * @return The aliases
     */
    public String[] getAliases() {
        return aliases;
    }
    
    /**
     * Converts to this unit from milliseconds
     *
     * @param milliseconds The milliseconds
     * @return The converted value
     */
    public double fromMillis(long milliseconds) {
        return milliseconds / (this.msPerUnit * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to milliseconds
     *
     * @param duration The duration
     * @return The converted value
     */
    public long toMillis(long duration) {
        return msPerUnit * duration;
    }
    
    /**
     * Converts the duration using this unit as a base to ticks (50ms) (Minecraft based)
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toTicks(long duration) {
        return toMillis(duration) / (TICKS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to seconds
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toSeconds(long duration) {
        return toMillis(duration) / (SECONDS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to minutes
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toMinutes(long duration) {
        return toMillis(duration) / (MINUTES.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to hours
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toHours(long duration) {
        return toMillis(duration) / (HOURS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to days
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toDays(long duration) {
        return toMillis(duration) / (DAYS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to weeks
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toWeeks(long duration) {
        return toMillis(duration) / (WEEKS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to months
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toMonths(long duration) {
        return toMillis(duration) / (MONTHS.toMillis(1) * 1.0);
    }
    
    /**
     * Converts the duration using this unit as a base to years
     *
     * @param duration The duration
     * @return The converted value
     */
    public double toYears(long duration) {
        return toMillis(duration) / (YEARS.toMillis(1) * 1.0);
    }
    
    /**
     * The amount of milliseconds per one of this unit
     *
     * @return The millisecond amount
     */
    public long getMsPerUnit() {
        return msPerUnit;
    }
    
    /**
     * Finds a unit based on the name and aliases
     *
     * @param unitString The string
     * @return The unit that was found
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