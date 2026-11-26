package com.stardevllc.starlib.units;

public enum VolumeUnit implements Unit {
    CUBIC_METER(1.0),
    LITER(0.001),
    MILLILITER(1e-6),
    MICROLITER(1e-9),
    CUBIC_CENTIMETER(1e-6),
    CUBIC_INCH(1.6387064e-5),
    CUBIC_FOOT(0.028316846592),
    US_FLUID_OUNCE(2.95735295625e-5),
    US_GALLON(0.003785411784);
    
//    TEASPOON(4.92892),
//    TABLESPOON(14.7868),
//    CUP(240),
//    PINT(473.176),
//    QUART(946.353),
    
    private final double amountInBase;
    private final String[] aliases;
    
    VolumeUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public double toBase(double value) {
        return value * amountInBase;
    }
    
    public double fromBase(double baseValue) {
        return baseValue / amountInBase;
    }
    
    @Override
    public double getAmountInBase() {
        return amountInBase;
    }
    
    @Override
    public String[] getAliases() {
        return aliases;
    }
    
    public static VolumeUnit matchUnit(String str) {
        try {
            return VolumeUnit.valueOf(str.toUpperCase());
        } catch (Throwable t) {
        }
        
        for (VolumeUnit unit : values()) {
            if (unit.getAliases() != null) {
                for (String alias : unit.getAliases()) {
                    if (alias.equalsIgnoreCase(str)) {
                        return unit;
                    }
                }
            }
        }
        
        return null;
    }
}