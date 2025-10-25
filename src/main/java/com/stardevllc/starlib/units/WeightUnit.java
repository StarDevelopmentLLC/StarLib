package com.stardevllc.starlib.units;

public enum WeightUnit {
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
    
    public double toGrams(double weight) {
        return getAmountInBase() * weight;
    }
    
    public double toOunces(double weight) {
        return getAmountInBase() * weight / OUNCE.getAmountInBase();
    }
    
    public double toPounds(double weight) {
        return getAmountInBase() * weight / POUND.getAmountInBase();
    }
    
    public double toTons(double weight) {
        return getAmountInBase() * weight / TON.getAmountInBase();
    }
    
    public double toKilograms(double weight) {
        return getAmountInBase() * weight / KILOGRAM.getAmountInBase();
    }
    
    public double toMetricTon(double weight) {
        return getAmountInBase() * weight / METRIC_TON.getAmountInBase();
    }
}