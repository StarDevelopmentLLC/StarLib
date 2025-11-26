package com.stardevllc.starlib.units;

public enum WeightUnit implements Unit {
    GRAM(1, "g"), 
    OUNCE(28.3495, "oz"), 
    POUND(453.592, "lb"), 
    TON(907185, "T"), 
    KILOGRAM(1000, "kg"), 
    METRIC_TON(1000000, "t");
    
    private final double amountInBase;
    private final String[] aliases;
    
    WeightUnit(double amountInBase, String... aliases) {
        this.amountInBase = amountInBase;
        this.aliases = aliases;
    }
    
    public double getAmountInBase() {
        return amountInBase;
    }
    
    public String[] getAliases() {
        return aliases;
    }
    
    public double toUnit(double weight, WeightUnit newUnit) {
        return getAmountInBase() * weight / newUnit.getAmountInBase();
    }
    
    public double toGrams(double weight) {
        return getAmountInBase() * weight;
    }
    
    public double toOunces(double weight) {
        return toUnit(weight, OUNCE);
    }
    
    public double toPounds(double weight) {
        return toUnit(weight, POUND);
    }
    
    public double toTons(double weight) {
        return toUnit(weight, TON);
    }
    
    public double toKilograms(double weight) {
        return toUnit(weight, KILOGRAM);
    }
    
    public double toMetricTon(double weight) {
        return toUnit(weight, METRIC_TON);
    }
}