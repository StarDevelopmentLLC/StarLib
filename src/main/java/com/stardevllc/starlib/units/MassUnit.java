package com.stardevllc.starlib.units;

public enum MassUnit implements Unit {
    MILLIGRAM(1e-6),
    GRAM(1e-3),
    KILOGRAM(1.0),
    METRIC_TON(1_000.0),
    OUNCE(0.028349523125),
    POUND(0.45359237),
    SHORT_TON(907.18474);
    
    private final double amountInBase;
    private final String[] aliases;
    
    MassUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public double toBase(double value) {
        return value * amountInBase;
    }
    
    public double fromBase(double baseValue) {
        return baseValue / amountInBase;
    }
    
    public double getAmountInBase() {
        return amountInBase;
    }
    
    public String[] getAliases() {
        return aliases;
    }
}