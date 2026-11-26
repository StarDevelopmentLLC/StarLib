package com.stardevllc.starlib.units;

public enum DensityUnit implements Unit {
    KG_PER_CUBIC_METER(1.0),
    G_PER_CUBIC_CM(1000.0),
    KG_PER_LITER(1000.0),
    LB_PER_CUBIC_FOOT(16.018463),
    LB_PER_GALLON_US(119.826427);
    
    private final double amountInBase;
    private final String[] aliases;
    
    DensityUnit(double amountInBase, String... aliases) {
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
}