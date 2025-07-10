package com.stardevllc.units;

import com.stardevllc.converter.string.EnumStringConverter;
import com.stardevllc.converter.string.StringConverters;

public enum LengthUnit {
    MILLIMETER(1, "millimeters", "mm"), //Base Unit
    CENTIMETER(10, "centimeters", "cm"), 
    INCH(25.4, "inch", "in"), 
    FOOT(304.8, "feet", "ft"), 
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

    public double getAmountInBase() {
        return amountInBase;
    }

    public String[] getAliases() {
        return aliases;
    }

    public double toMillimeters(double length) {
        return getAmountInBase() * length;
    }
    
    public double toCentimeters(double length) {
        return getAmountInBase() * length / CENTIMETER.getAmountInBase();
    }

    public double toInches(double length) {
        return getAmountInBase() * length / INCH.getAmountInBase();
    }

    public double toFeed(double length) {
        return getAmountInBase() * length / FOOT.getAmountInBase();
    }

    public double toYards(double length) {
        return getAmountInBase() * length / YARD.getAmountInBase();
    }
}