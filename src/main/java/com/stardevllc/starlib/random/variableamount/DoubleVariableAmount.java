package com.stardevllc.starlib.random.variableamount;

import java.util.Random;

abstract class DoubleVariableAmount implements VariableAmount {
    protected static final int PRIME = 59;
    
    protected final double base;
    
    private DoubleVariableAmount(double base) {
        this.base = base;
    }
    
    @Override
    public int getInt(Random random) {
        return (int) getDouble(random);
    }
    
    @Override
    public long getLong(Random random) {
        return (long) getDouble(random);
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        return result * PRIME + Long.hashCode(Double.doubleToLongBits(this.base));
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DoubleVariableAmount other)) {
            return false;
        }
        return Double.compare(this.base, other.base) == 0;
    }
    
    static final class Fixed extends DoubleVariableAmount {
        Fixed(double base) {
            super(base);
        }
        
        @Override
        public double getDouble(Random random) {
            return base;
        }
        
        @Override
        public String toString() {
            return "DoubleVariableAmount.Fixed(amount=" + this.getDouble() + ")";
        }
    }
    
    static final class Range extends DoubleVariableAmount {
        private final double max, divisor;
        private final int places;
        
        Range(double min, double max, double divisor, int places) {
            super(min);
            this.max = max;
            this.divisor = divisor;
            this.places = places;
        }
        
        Range(double min, double max, double divisor) {
            this(min, max, divisor, 0);
        }
        
        Range(double min, double max) {
            this(min, max, 1);
        }
        
        @Override
        public double getDouble(Random random) {
            double value = random.nextDouble(base, max) / divisor;
            if (places > 0) {
                double pow = Math.pow(10, places);
                return Math.round(value * pow) / pow;
            }
            return value;
        }
        
        @Override
        public String toString() {
            return "DoubleVariableAmount.BaseAndVariance(min=" + this.base + ", max=" + this.max + ", divisor=" + this.divisor + ")";
        }
        
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Range range)) {
                return false;
            }
            if (!super.equals(object)) {
                return false;
            }
            
            return Double.compare(max, range.max) == 0 && Double.compare(divisor, range.divisor) == 0 && places == range.places;
        }
        
        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + Double.hashCode(max);
            result = 31 * result + Double.hashCode(divisor);
            result = 31 * result + places;
            return result;
        }
    }
    
    static final class BaseAndVariance extends DoubleVariableAmount {
        private final VariableAmount variance;
        private final double divisor;
        private final int places;
        
        BaseAndVariance(double base, VariableAmount variance, double divisor, int places) {
            super(base);
            this.variance = variance;
            this.divisor = divisor;
            this.places = places;
        }
        
        BaseAndVariance(double base, VariableAmount variance, double divisor) {
            this(base, variance, divisor, 0);
        }
        
        BaseAndVariance(double base, VariableAmount variance) {
            this(base, variance, 1);
        }
        
        @Override
        public double getDouble(Random random) {
            double var = this.variance.getDouble(random);
            double value = (this.base + random.nextDouble() * var * 2 - var) / divisor;
            if (places > 0) {
                double pow = Math.pow(10, places);
                return Math.round(value * pow) / pow;
            }
            return value;
        }
        
        @Override
        public String toString() {
            return "DoubleVariableAmount.BaseAndVariance(base=" + this.base + ", variance=" + this.variance + ", divisor=" + this.divisor + ")";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BaseAndVariance other)) {
                return false;
            }
            return super.equals(o) && this.variance.equals(other.variance) && Double.compare(this.divisor, other.divisor) == 0;
        }
        
        @Override
        public int hashCode() {
            int result = super.hashCode() * PRIME + this.variance.hashCode();
            return result * PRIME + Double.hashCode(this.divisor);
        }
    }
    
    static final class BaseAndAddition extends DoubleVariableAmount {
        private final VariableAmount addition;
        
        BaseAndAddition(double base, VariableAmount addition) {
            super(base);
            this.addition = addition;
        }
        
        @Override
        public double getDouble(Random random) {
            return this.base + random.nextDouble() * this.addition.getDouble(random);
        }
        
        @Override
        public String toString() {
            return "DoubleVariableAmount.BaseAndAddition(base=" + this.base + ", addition=" + this.addition + ")";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BaseAndAddition other)) {
                return false;
            }
            return super.equals(o) && this.addition.equals(other.addition);
        }
        
        @Override
        public int hashCode() {
            return super.hashCode() * PRIME + this.addition.hashCode();
        }
    }
    
    static final class OptionalAmount extends DoubleVariableAmount {
        private final double chance;
        private final VariableAmount inner;
        
        OptionalAmount(double base, double chance, VariableAmount inner) {
            super(base);
            this.chance = chance;
            this.inner = inner;
        }
        
        @Override
        public double getDouble(Random random) {
            if (random.nextDouble() < this.chance) {
                return this.inner.getDouble(random);
            }
            return this.base;
        }
        
        @Override
        public String toString() {
            return "DoubleVariableAmount.OptionalAmount(base=" + this.base + ", chance=" + this.chance + ", inner=" + this.inner + ")";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof OptionalAmount other)) {
                return false;
            }
            return super.equals(o) && Double.compare(this.chance, other.chance) == 0 && this.inner.equals(other.inner);
        }
        
        @Override
        public int hashCode() {
            final int PRIME = 59;
            int result = super.hashCode();
            result = result * PRIME + Long.hashCode(Double.doubleToLongBits(this.chance));
            return result * PRIME + this.inner.hashCode();
        }
    }
}