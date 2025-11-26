package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.objects.factory.IFactory;
import com.stardevllc.starlib.random.Bounds;

import java.util.Objects;

public record Volume(double value, VolumeUnit unit) {
    public double get(VolumeUnit newUnit) {
        return unit.toUnit(value, newUnit);
    }
    
    public double get() {
        return value;
    }
    
    public Volume add(Volume other) {
        double totalValue = this.value + other.get(this.unit);
        return new Volume(totalValue, this.unit);
    }
    
    public Volume add(double d) {
        return new Volume(this.value + d, this.unit);
    }
    
    public Volume subtract(Volume other) {
        double totalValue = this.value - other.get(this.unit);
        return new Volume(totalValue, this.unit);
    }
    
    public Volume subtract(double d) {
        return new Volume(this.value - d, this.unit);
    }
    
    public Volume multiply(Volume other) {
        double totalValue = this.value * other.get(this.unit);
        return new Volume(totalValue, this.unit);
    }
    
    public Volume multiply(double d) {
        return new Volume(this.value * d, this.unit);
    }
    
    public Volume divide(Volume other) {
        double totalValue = this.value / other.get(this.unit);
        return new Volume(totalValue, this.unit);
    }
    
    public Volume divide(double d) {
        return new Volume(this.value / d, this.unit);
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
    
    public static class Builder implements IBuilder<Volume, Builder> {
        
        private double value;
        private VolumeUnit unit;
        
        public Builder() {}
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Volume length) {
            this.value = length.value;
            this.unit = length.unit;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(VolumeUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Volume build() {
            return new Volume(value, unit);
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory implements IFactory<Volume, Factory> {
        
        private Bounds bounds;
        private VolumeUnit unit;
        
        public Factory() {}
        
        public Factory(Factory factory) {
            this.bounds = factory.bounds;
            this.unit = factory.unit;
        }
        
        public Factory bounds(Bounds bounds, VolumeUnit unit) {
            this.bounds = bounds;
            this.unit = unit;
            return self();
        }
        
        public Factory bounds(long min, long max, long divisor, VolumeUnit unit) {
            return bounds(Bounds.range(min, max, divisor), unit);
        }
        
        public Factory bounds(long min, long max, VolumeUnit unit) {
            return bounds(Bounds.range(min, max), unit);
        }
        
        @Override
        public Volume create(Object[] parameters) {
            Objects.requireNonNull(this.bounds, "bounds must not be null");
            Objects.requireNonNull(this.unit, "unit must not be null");
            return new Volume(bounds.generateAndDivide(), unit);
        }
    }
}