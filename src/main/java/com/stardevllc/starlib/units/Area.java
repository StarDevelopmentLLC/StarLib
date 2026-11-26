package com.stardevllc.starlib.units;

public record Area(double squareMeters) implements Comparable<Area> {
    
    public static Area of(double value, AreaUnit unit) {
        return new Area(unit.toSquareMeters(value));
    }
    
    public double to(AreaUnit targetUnit) {
        return targetUnit.fromSquareMeters(this.squareMeters);
    }
    
    public Area add(Area other) {
        return new Area(this.squareMeters + other.squareMeters);
    }
    
    public Area subtract(Area other) {
        return new Area(this.squareMeters - other.squareMeters);
    }
    
    public Area multiply(double scalar) {
        return new Area(this.squareMeters * scalar);
    }
    
    public Volume multiply(Length height) {
        return new Volume(this.squareMeters * height.meters());
    }
    
    public Length divide(Length width) {
        return new Length(this.squareMeters / width.meters());
    }
    
    public Length sqrt() {
        return new Length(Math.sqrt(this.squareMeters));
    }
    
    @Override
    public int compareTo(Area other) {
        return Double.compare(this.squareMeters, other.squareMeters);
    }
}