package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.ObjectProvider;
import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;
import org.jetbrains.annotations.NotNull;

public record Density(double kgPerCubicMeter) implements Comparable<Density> {
    public Density {
        if (kgPerCubicMeter < 0) {
            throw new IllegalArgumentException("Density cannot be negative");
        }
    }
    
    public Density(double amount, DensityUnit unit) {
        this(unit.toBase(amount));
    }
    
    public double to(DensityUnit targetUnit) {
        return targetUnit.fromBase(this.kgPerCubicMeter);
    }
    
    public Density add(Density other) {
        return new Density(this.kgPerCubicMeter + other.kgPerCubicMeter);
    }
    
    public Density subtract(Density other) {
        return new Density(this.kgPerCubicMeter - other.kgPerCubicMeter);
    }
    
    public Density multiply(double factor) {
        return new Density(this.kgPerCubicMeter * factor);
    }
    
    public Density divide(double divisor) {
        return new Density(this.kgPerCubicMeter / divisor);
    }
    
    public Mass multiplyBy(Volume volume) {
        return new Mass(this.kgPerCubicMeter * volume.cubicMeters());
    }
    
    public double calculateMassKg(double cubicMeters) {
        return this.kgPerCubicMeter * cubicMeters;
    }
    
    @Override
    public int compareTo(@NotNull Density other) {
        return Double.compare(this.kgPerCubicMeter, other.kgPerCubicMeter);
    }
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static Density of(double kgPerCubicMeter) {
        return new Density(kgPerCubicMeter);
    }
    
    public static Density of(double amount, DensityUnit unit) {
        return new Density(amount, unit);
    }
    
    public static final class Builder extends AbstractBuilder<Density, Builder> {
        
        private double value;
        private DensityUnit unit = DensityUnit.KG_PER_CUBIC_METER;
        
        public Builder() {}
        
        public Builder(Density density) {
            this.value = density.kgPerCubicMeter;
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(DensityUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Density newInstance() {
            return new Density(unit.toBase(value));
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static final class Factory extends AbstractFactory<Density, Factory> {
        
        private final ObjectProvider<Double> valueProvider = new ObjectProvider<>();
        private DensityUnit unit = DensityUnit.KG_PER_CUBIC_METER;
        
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
        
        public Factory unit(DensityUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
        
        @Override
        public Density create(Object[] parameters) {
            return new Density(unit.toBase(valueProvider.get()));
        }
    }
}
