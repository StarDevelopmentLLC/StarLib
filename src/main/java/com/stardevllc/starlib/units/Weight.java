package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.objects.factory.IFactory;
import com.stardevllc.starlib.random.Bounds;

import java.util.Objects;

public final class Weight {
    
    public static final WeightUnit DEFAULT_UNIT = WeightUnit.POUND;
    
    private long id;
    
    private final double value;
    private final WeightUnit unit;
    
    public Weight(double value, WeightUnit unit) {
        this.value = value;
        this.unit = unit;
    }
    
    public double get(WeightUnit newUnit) {
        return unit.toUnit(value, newUnit);
    }
    
    public double get() {
        return value;
    }
    
    public Weight add(Weight other) {
        double totalValue = this.value + other.get(this.unit);
        return new Weight(totalValue, this.unit);
    }
    
    public Weight add(double d) {
        return new Weight(this.value + d, this.unit);
    }
    
    public Weight subtract(Weight other) {
        double totalValue = this.value - other.get(this.unit);
        return new Weight(totalValue, this.unit);
    }
    
    public Weight subtract(double d) {
        return new Weight(this.value - d, this.unit);
    }
    
    public Weight multiply(Weight other) {
        double totalValue = this.value * other.get(this.unit);
        return new Weight(totalValue, this.unit);
    }
    
    public Weight multiply(double d) {
        return new Weight(this.value * d, this.unit);
    }
    
    public Weight divide(Weight other) {
        double totalValue = this.value / other.get(this.unit);
        return new Weight(totalValue, this.unit);
    }
    
    public Weight divide(double d) {
        return new Weight(this.value / d, this.unit);
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
    
    public double value() {
        return value;
    }
    
    public WeightUnit unit() {
        return unit;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Weight) obj;
        return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value) &&
                this.unit == that.unit;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
    
    @Override
    public String toString() {
        return "Weight[" +
                "value=" + value + ", " +
                "unit=" + unit + ']';
    }
    
    public static class Builder implements IBuilder<Weight, Builder> {
        
        private double value;
        private WeightUnit unit;
        
        public Builder() {
        }
        
        public Builder(Builder builder) {
            this.value = builder.value;
            this.unit = builder.unit;
        }
        
        public Builder(Weight weight) {
            this.value = weight.value;
            this.unit = weight.unit;
        }
        
        public Builder value(double value) {
            this.value = value;
            return self();
        }
        
        public Builder unit(WeightUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public Weight build() {
            return new Weight(value, unit);
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
    
    public static class Factory implements IFactory<Weight, Factory> {
        
        private Bounds bounds;
        private WeightUnit unit;
        
        public Factory() {
        }
        
        public Factory(Factory factory) {
            this.bounds = factory.bounds;
            this.unit = factory.unit;
        }
        
        public Factory bounds(Bounds bounds, WeightUnit unit) {
            this.bounds = bounds;
            this.unit = unit;
            return self();
        }
        
        public Factory bounds(long min, long max, long divisor, WeightUnit unit) {
            return bounds(Bounds.range(min, max, divisor), unit);
        }
        
        public Factory bounds(long min, long max, WeightUnit unit) {
            return bounds(Bounds.range(min, max), unit);
        }
        
        @Override
        public Weight create(Object[] parameters) {
            Objects.requireNonNull(bounds, "bounds cannot be null");
            Objects.requireNonNull(unit, "unit cannot be null");
            return new Weight(bounds.generateAndDivide(), unit);
        }
    }
}