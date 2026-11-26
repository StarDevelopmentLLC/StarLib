package com.stardevllc.starlib.units;

public enum AreaUnit {
    SQUARE_MILLIMETER(1e-6),
    SQUARE_CENTIMETER(1e-4),
    SQUARE_METER(1.0),
    HECTARE(10_000.0),
    SQUARE_KILOMETER(1e6),
    SQUARE_INCH(0.00064516),
    SQUARE_FOOT(0.09290304),
    SQUARE_YARD(0.83612736),
    ACRE(4046.8564224),
    SQUARE_MILE(2_589_988.110336);
    
    private final double toSquareMetersFactor;
    
    AreaUnit(double toSquareMetersFactor) {
        this.toSquareMetersFactor = toSquareMetersFactor;
    }
    
    public double toSquareMeters(double value) {
        return value * toSquareMetersFactor;
    }
    
    public double fromSquareMeters(double squareMeters) {
        return squareMeters / toSquareMetersFactor;
    }
}