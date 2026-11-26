package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.ObjectProvider;
import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;

public record Length(double meters) implements Comparable<Length> {
    
    public Length(double value, LengthUnit unit) {
        this(unit.toBase(value));
    }
    
    public double to(LengthUnit unit) {
        return unit.fromBase(this.meters);
    }
    
    public Length add(Length other) {
        return new Length(this.meters + other.meters);
    }
    
    public Length subtract(Length other) {
        return new Length(this.meters - other.meters);
    }
    
    public Length scale(double factor) {
        return new Length(this.meters * factor);
    }
    
    public double ratio(Length other) {
        return this.meters / other.meters;
    }
    
    @Override
    public int compareTo(Length other) {
        return Double.compare(this.meters, other.meters);
    }
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static Length of(double meters) {
        return new Length(meters);
    }
    
    public static Length of(double value, LengthUnit unit) {
        return new Length(value, unit);
    }
    
    public static class Builder extends AbstractBuilder<Length, Builder> {
        
        private double value;
        private LengthUnit unit = LengthUnit.METERS;
        
        public Builder() {
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Length length) {
            this.value = length.meters;
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
        public Length newInstance() {
            return new Length(unit.toBase(this.value));
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory extends AbstractFactory<Length, Factory> {
        
        private final ObjectProvider<Double> valueProvider = new ObjectProvider<>();
        private LengthUnit unit = LengthUnit.METERS;
        
        public Factory() {
        }
        
        public Factory(Factory factory) {
            this.valueProvider.set(factory.valueProvider);
            this.unit = factory.unit;
        }
        
        public Factory valueProvider(ObjectProvider<Double> provider) {
            this.valueProvider.set(provider);
            return self();
        }
        
        public Factory unit(LengthUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Length create(Object[] parameters) {
            return new Length(unit.toBase(valueProvider.get()));
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
    }
}