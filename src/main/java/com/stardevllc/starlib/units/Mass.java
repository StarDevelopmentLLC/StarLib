package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.ObjectProvider;
import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;

public record Mass(double kilograms) implements Comparable<Mass> {
    
    public Mass(double value, MassUnit unit) {
        this(unit.toBase(value));
    }
    
    public double to(MassUnit unit) {
        return unit.fromBase(this.kilograms);
    }
    
    public Mass add(Mass other) {
        return new Mass(this.kilograms + other.kilograms);
    }
    
    public Mass subtract(Mass other) {
        return new Mass(this.kilograms - other.kilograms);
    }
    
    public Mass multiply(double factor) {
        return new Mass(this.kilograms * factor);
    }
    
    public Mass divide(double factor) {
        return new Mass(this.kilograms / factor);
    }
    
    @Override
    public int compareTo(Mass other) {
        return Double.compare(this.kilograms, other.kilograms);
    }
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static Mass of(double kilograms) {
        return new Mass(kilograms);
    }
    
    public static Mass of(double value, MassUnit unit) {
        return new Mass(value, unit);
    }
    
    public static class Builder extends AbstractBuilder<Mass, Builder> {
        
        private double value;
        private MassUnit unit = MassUnit.KILOGRAM;
        
        public Builder() {
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Mass weight) {
            this.value = weight.kilograms;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(MassUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Mass newInstance() {
            return new Mass(unit.toBase(this.value));
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory extends AbstractFactory<Mass, Factory> {
        
        private final ObjectProvider<Double> valueProvider = new ObjectProvider<>();
        private MassUnit unit;
        
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
        
        public Factory unit(MassUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Mass create(Object[] parameters) {
            return new Mass(unit.toBase(valueProvider.get()));
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
    }
}