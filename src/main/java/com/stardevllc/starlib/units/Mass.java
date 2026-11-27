package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;
import com.stardevllc.starlib.objects.provider.DoubleProvider;
import com.stardevllc.starlib.random.RandomSelector;
import com.stardevllc.starlib.random.variableamount.VariableAmount;

import java.util.function.DoubleSupplier;

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
    
    public Volume divide(Density density) {
        return new Volume(this.kilograms / density.kgPerCubicMeter());
    }
    
    public Density divideBy(Volume volume) {
        return new Density(this.kilograms / volume.cubicMeters());
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
    
    public static class Factory extends AbstractFactory<Mass, Builder, Factory> {
        
        private final DoubleProvider valueProvider = new DoubleProvider();
        private MassUnit unit;
        
        public Factory() {
            super(Builder::new);
        }
        
        public Factory(Factory factory) {
            super(factory);
            this.valueProvider.set(factory.valueProvider);
            this.unit = factory.unit;
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
        
        public Factory unit(MassUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        protected void applyToBuilder(Builder builder, Object[] params) {
            builder.unit(unit).value(valueProvider.get());
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
    }
}