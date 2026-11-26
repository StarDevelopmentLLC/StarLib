package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.ObjectProvider;
import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;
import org.jetbrains.annotations.NotNull;

public record Volume(double cubicMeters) implements Comparable<Volume> {
    public Volume {
        if (cubicMeters < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
    }
    
    public Volume(double value, VolumeUnit unit) {
        this(unit.toBase(value));
    }
    
    public double to(VolumeUnit targetUnit) {
        return targetUnit.fromBase(this.cubicMeters);
    }
    
    public Volume add(Volume other) {
        return new Volume(this.cubicMeters + other.cubicMeters);
    }
    
    public Volume subtract(Volume other) {
        return new Volume(this.cubicMeters - other.cubicMeters);
    }
    
    public Volume multiply(double factor) {
        return new Volume(this.cubicMeters * factor);
    }
    
    public Volume divide(double divisor) {
        return new Volume(this.cubicMeters / divisor);
    }
    
    @Override
    public int compareTo(@NotNull Volume other) {
        return Double.compare(this.cubicMeters, other.cubicMeters);
    }
    
    public Builder asBuilder() {
        return new Builder(this);
    }
    
    public static Volume of(double cubicMeters) {
        return new Volume(cubicMeters);
    }
    
    public static Volume of(double value, VolumeUnit unit) {
        return new Volume(value, unit);
    }
    
    public static class Builder extends AbstractBuilder<Volume, Builder> {
        
        private double value;
        private VolumeUnit unit = VolumeUnit.CUBIC_METER;
        
        public Builder() {
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Volume length) {
            this.value = length.cubicMeters;
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
        public Volume newInstance() {
            return new Volume(unit.toBase(value));
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory extends AbstractFactory<Volume, Factory> {
        
        private final ObjectProvider<Double> valueProvider = new ObjectProvider<>();
        private VolumeUnit unit;
        
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
        
        public Factory unit(VolumeUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Volume create(Object[] parameters) {
            return new Volume(unit.toBase(valueProvider.get()));
        }
        
        @Override
        public Factory clone() {
            return new Factory(this);
        }
    }
}