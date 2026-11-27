package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;
import com.stardevllc.starlib.objects.provider.DoubleProvider;
import com.stardevllc.starlib.random.RandomSelector;
import com.stardevllc.starlib.random.variableamount.VariableAmount;

import java.util.function.DoubleSupplier;

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
        
        public Builder() {
        }
        
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
    
    public static final class Factory extends AbstractFactory<Area, Builder, Factory> {
        
        private final DoubleProvider valueProvider = new DoubleProvider();
        private AreaUnit unit = AreaUnit.SQUARE_METER;
        
        public Factory() {
            super(Builder::new);
        }
        
        public Factory(Factory factory) {
            super(factory);
            this.valueProvider.set(factory.valueProvider);
            this.unit = factory.unit;
        }
        
        @Override
        protected void applyToBuilder(Builder builder, Object[] params) {
            builder.unit(unit).value(valueProvider.get());
        }
        
        public Factory valueProvider(DoubleProvider provider) {
            this.valueProvider.set(provider);
            return self();
        }
        
        public Factory valueProvider(RandomSelector<Double> provider) {
            this.valueProvider.set(provider);
            return self();
        }
        
        public Factory valueProvider(DoubleSupplier provider) {
            this.valueProvider.set(provider);
            return self();
        }
        
        public Factory valueProvider(VariableAmount provider) {
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
    }
}