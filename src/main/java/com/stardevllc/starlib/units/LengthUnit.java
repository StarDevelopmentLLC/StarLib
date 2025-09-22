package com.stardevllc.starlib.units;

import com.stardevllc.starlib.converter.string.EnumStringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

/**
 * Represents a unit of length
 */
public enum LengthUnit {
    /**
     * Millimeters (base unit)
     */
    MILLIMETER(1, "millimeters", "mm"),
    
    /**
     * Centimeters (millimeters * 10)
     */
    CENTIMETER(10, "centimeters", "cm"),
    
    /**
     * Inches (millimeters * 25.4)
     */
    INCH(25.4, "inch", "in"),
    
    /**
     * Foot (millimeters * 304.8)
     */
    FOOT(304.8, "feet", "ft"),
    
    /**
     * Year (Millimeters * 914.4)
     */
    YARD(914.4, "yard", "yd", "yds");
    
    static {
        StringConverters.addConverter(LengthUnit.class, new EnumStringConverter<>(LengthUnit.class));
    }
    
    private final double amountInBase;
    private final String[] aliases;
    
    LengthUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    /**
     * Matches the unit from a string
     *
     * @param unit The string representation
     * @return The length unit instance
     */
    public static LengthUnit matchUnit(String unit) {
        try {
            return LengthUnit.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            for (LengthUnit value : LengthUnit.values()) {
                if (value.getAliases() != null) {
                    for (String alias : value.getAliases()) {
                        if (alias.equalsIgnoreCase(unit)) {
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Amount in base
     *
     * @return Amount in base
     */
    public double getAmountInBase() {
        return amountInBase;
    }
    
    /**
     * Aliases
     *
     * @return aliases
     */
    public String[] getAliases() {
        return aliases;
    }
    
    /**
     * Converts to millimeters
     *
     * @param length The length
     * @return The converted value
     */
    public double toMillimeters(double length) {
        return getAmountInBase() * length;
    }
    
    /**
     * Converts to centimeters
     *
     * @param length The length
     * @return The converted value
     */
    public double toCentimeters(double length) {
        return getAmountInBase() * length / CENTIMETER.getAmountInBase();
    }
    
    /**
     * Converts to inches
     *
     * @param length The length
     * @return The converted value
     */
    public double toInches(double length) {
        return getAmountInBase() * length / INCH.getAmountInBase();
    }
    
    /**
     * Converts to feet
     *
     * @param length The length
     * @return The converted value
     */
    public double toFeet(double length) {
        return getAmountInBase() * length / FOOT.getAmountInBase();
    }
    
    /**
     * Converts to yards
     *
     * @param length The length
     * @return The converted value
     */
    public double toYards(double length) {
        return getAmountInBase() * length / YARD.getAmountInBase();
    }
}