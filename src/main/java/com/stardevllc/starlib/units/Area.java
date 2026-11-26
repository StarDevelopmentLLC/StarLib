package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.ObjectProvider;
import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;

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
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static final class Builder extends AbstractBuilder<Area, Builder> {
        
        private double value;
        private AreaUnit unit = AreaUnit.SQUARE_METER;
        
        public Builder() {}
        
        public Builder(Area area) {
            this.value = area.squareMeters;
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(AreaUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Area newInstance() {
            return new Area(unit.toSquareMeters(value));
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static final class Factory extends AbstractFactory<Area, Factory> {
        
        private final ObjectProvider<Double> valueProvider = new ObjectProvider<>();
        private AreaUnit unit = AreaUnit.SQUARE_METER;
        
        public Factory() {
        }
        
        public Factory(Factory factory) {
            super(factory);
            this.valueProvider.set(factory.valueProvider);
            this.unit = factory.unit;
        }
        
        public Factory valueProvider(ObjectProvider<Double> provider) {
            this.valueProvider.set(provider);
            return self();
        }
        
        public Factory unit(AreaUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
        
        @Override
        public Area create(Object[] parameters) {
            return new Area(unit.toSquareMeters(valueProvider.get()));
        }
    }
}