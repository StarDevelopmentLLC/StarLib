package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.AbstractBuilder;
import com.stardevllc.starlib.objects.factory.AbstractFactory;
import com.stardevllc.starlib.objects.provider.DoubleProvider;
import com.stardevllc.starlib.random.RandomSelector;
import com.stardevllc.starlib.random.variableamount.VariableAmount;

import java.util.function.DoubleSupplier;

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
    
    public Area multiply(Length other) {
        return new Area(this.meters * other.meters);
    }
    
    public Area squared() {
        return new Area(this.meters * this.meters);
    }
    
    public Volume multiply(Area area) {
        return new Volume(this.meters * area.squareMeters());
    }
    
    public Volume cubed() {
        return new Volume(this.meters * this.meters * this.meters);
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
    
    public static class Factory extends AbstractFactory<Length, Builder, Factory> {
        
        private final DoubleProvider valueProvider = new DoubleProvider();
        private LengthUnit unit = LengthUnit.METERS;
        
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
        
        public Factory unit(LengthUnit unit) {
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