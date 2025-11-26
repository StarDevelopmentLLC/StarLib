package com.stardevllc.starlib.units;

public enum VolumeUnit implements Unit {
    MILLILITER(1),
    TEASPOON(4.92892),
    TABLESPOON(14.7868),
    CUBIC_INCH(16.3871),
    FLUID_OUNCE(29.5735),
    CUP(240),
    PINT(473.176),
    QUART(946.353),
    GALLON(3785.41),
    CUBIC_FOOT(28316.8),
    CUBIC_METER(1E6);
    
    private final double amountInBase;
    private final String[] aliases;
    
    VolumeUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public double toUnit(double volume, VolumeUnit newUnit) {
        return getAmountInBase() * volume / newUnit.getAmountInBase();
    }
    
    public double toMilliliters(double volume) {
        return getAmountInBase() * volume;
    }
    
    public double toTeaspoon(double volume) {
        return toUnit(volume, TEASPOON);
    }
    
    public double toTablespoon(double volume) {
        return toUnit(volume, TABLESPOON);
    }
    
    public double toCubicInch(double volume) {
        return toUnit(volume, CUBIC_INCH);
    }
    
    public double toFluidOunce(double volume) {
        return toUnit(volume, FLUID_OUNCE);
    }
    
    public double toCup(double volume) {
        return toUnit(volume, CUP);
    }
    
    public double toPint(double volume) {
        return toUnit(volume, PINT);
    }
    
    public double toQuart(double volume) {
        return toUnit(volume, QUART);
    }
    
    public double toGallon(double volume) {
        return toUnit(volume, GALLON);
    }
    
    public double toCubicFoot(double volume) {
        return toUnit(volume, CUBIC_FOOT);
    }
    
    public double toCubicMeter(double volume) {
        return toUnit(volume, CUBIC_METER);
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