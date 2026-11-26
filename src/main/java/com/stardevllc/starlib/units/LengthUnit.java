package com.stardevllc.starlib.units;

import com.stardevllc.starlib.converter.string.EnumStringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

public enum LengthUnit implements Unit {
    NANOMETERS(1e-9),
    MICROMETERS(1e-6),
    MILLIMETERS(0.001),
    CENTIMETERS(0.01),
    METERS(1.0),
    KILOMETERS(1000.0),
    INCHES(0.0254),
    FEET(0.3048),
    YARDS(0.9144),
    MILES(1609.344);
    
    static {
        StringConverters.addConverter(LengthUnit.class, new EnumStringConverter<>(LengthUnit.class));
    }
    
    private final double amountInBase;
    private final String[] aliases;
    
    LengthUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public double toBase(double value) {
        return value * amountInBase;
    }
    
    public double fromBase(double baseValue) {
        return baseValue / amountInBase;
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
}