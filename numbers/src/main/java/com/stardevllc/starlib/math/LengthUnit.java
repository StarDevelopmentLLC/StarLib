package com.stardevllc.starlib.math;

public enum LengthUnit {
    MILLIMETER(1), //Base Unit
    CENTIMETER(0.1), 
    INCH(25.4), 
    FOOT(304.8), 
    YARD(914.4);
    
    private final double amountInBase;

    LengthUnit(double amountInBase) {
        this.amountInBase = amountInBase;
    }

    public double getAmountInBase() {
        return amountInBase;
    }
    
    public double toMillimeters(double length) {
        return getAmountInBase() * length;
    }
    
    public double toCentimeters(double length) {
        return (getAmountInBase() * length) / CENTIMETER.getAmountInBase();
    }

    public double toInches(double length) {
        return (getAmountInBase() * length) / INCH.getAmountInBase();
    }

    public double toFeed(double length) {
        return (getAmountInBase() * length) / FOOT.getAmountInBase();
    }

    public double toYards(double length) {
        return (getAmountInBase() * length) / YARD.getAmountInBase();
    }
}