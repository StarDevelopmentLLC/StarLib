package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.objects.factory.IFactory;
import com.stardevllc.starlib.random.Bounds;

import java.util.Objects;

public record Length(double value, LengthUnit unit) {
    public double get(LengthUnit newUnit) {
        return unit.toUnit(value, newUnit);
    }
    
    public double get() {
        return value;
    }
    
    public Length add(Length other) {
        double totalValue = this.value + other.get(this.unit);
        return new Length(totalValue, this.unit);
    }
    
    public Length add(double d) {
        return new Length(this.value + d, this.unit);
    }
    
    public Length subtract(Length other) {
        double totalValue = this.value - other.get(this.unit);
        return new Length(totalValue, this.unit);
    }
    
    public Length subtract(double d) {
        return new Length(this.value - d, this.unit);
    }
    
    public Length multiply(Length other) {
        double totalValue = this.value * other.get(this.unit);
        return new Length(totalValue, this.unit);
    }
    
    public Length multiply(double d) {
        return new Length(this.value * d, this.unit);
    }
    
    public Length divide(Length other) {
        double totalValue = this.value / other.get(this.unit);
        return new Length(totalValue, this.unit);
    }
    
    public Length divide(double d) {
        return new Length(this.value / d, this.unit);
    }
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static Factory factory() {
        return new Factory();
    }
    
    public static class Builder implements IBuilder<Length, Builder> {
        
        private double value;
        private LengthUnit unit;
        
        public Builder() {}
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Length length) {
            this.value = length.value;
            this.unit = length.unit;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(LengthUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Length build() {
            return new Length(value, unit);
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory implements IFactory<Length, Factory> {
        
        private Bounds bounds;
        private LengthUnit unit;
        
        public Factory() {}
        
        public Factory(Factory factory) {
            this.bounds = factory.bounds;
            this.unit = factory.unit;
        }
        
        public Factory bounds(Bounds bounds, LengthUnit unit) {
            this.bounds = bounds;
            this.unit = unit;
            return self();
        }
        
        public Factory bounds(long min, long max, long divisor, LengthUnit unit) {
            return bounds(Bounds.range(min, max, divisor), unit);
        }
        
        public Factory bounds(long min, long max, LengthUnit unit) {
            return bounds(Bounds.range(min, max), unit);
        }
        
        @Override
        public Length create(Object[] parameters) {
            Objects.requireNonNull(this.bounds, "bounds must not be null");
            Objects.requireNonNull(this.unit, "unit must not be null");
            return new Length(bounds.generateAndDivide(), unit);
        }
    }
}