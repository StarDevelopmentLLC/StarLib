package com.stardevllc.starlib.random.variableamount;

import java.util.Random;

abstract class LongVariableAmount implements VariableAmount {
    protected static final int PRIME = 59;
    
    protected final long base;
    
    private LongVariableAmount(long base) {
        this.base = base;
    }
    
    @Override
    public double getDouble(Random random) {
        return getInt(random);
    }
    
    @Override
    public int getInt(Random random) {
        return (int) getLong(random);
    }
    
    @Override
    public int hashCode() {
        return PRIME + Long.hashCode(base);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LongVariableAmount other)) {
            return false;
        }
        return this.base == other.base;
    }
    
    static final class Fixed extends LongVariableAmount {
        Fixed(long base) {
            super(base);
        }
        
        @Override
        public long getLong(Random random) {
            return base;
        }
        
        @Override
        public String toString() {
            return "LongVariableAmount.Fixed(amount=" + this.getLong() + ")";
        }
    }
    
    static final class BaseAndVariance extends LongVariableAmount {
        private final VariableAmount variance;
        private final long divisor;
        
        BaseAndVariance(long base, VariableAmount variance, long divisor) {
            super(base);
            this.variance = variance;
            this.divisor = divisor;
        }
        
        public BaseAndVariance(long base, VariableAmount variance) {
            this(base, variance, 1);
        }
        
        @Override
        public long getLong(Random random) {
            long var = this.variance.getLong(random);
            return (this.base + random.nextLong(var + 1)) / divisor;
        }
        
        @Override
        public String toString() {
            return "LongVariableAmount.BaseAndVariance(base=" + this.base + ", variance=" + this.variance + ", divisor=" + divisor + ")";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BaseAndVariance other)) {
                return false;
            }
            return super.equals(o) && this.variance.equals(other.variance) && this.divisor == other.divisor;
        }
        
        @Override
        public int hashCode() {
            int result = super.hashCode() * PRIME + this.variance.hashCode();
            return result * PRIME + Long.hashCode(this.divisor);
        }
    }
    
    static final class BaseAndAddition extends LongVariableAmount {
        private final VariableAmount addition;
        
        BaseAndAddition(long base, VariableAmount addition) {
            super(base);
            this.addition = addition;
        }
        
        @Override
        public long getLong(Random random) {
            return this.base + random.nextLong(this.addition.getLong(random));
        }
        
        @Override
        public String toString() {
            return "LongVariableAmount.BaseAndAddition(base=" + this.base + ", addition=" + this.addition + ")";
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
    
    static final class OptionalAmount extends LongVariableAmount {
        private final double chance;
        private final VariableAmount inner;
        
        OptionalAmount(long base, double chance, VariableAmount inner) {
            super(base);
            this.chance = chance;
            this.inner = inner;
        }
        
        @Override
        public long getLong(Random random) {
            if (random.nextDouble() < this.chance) {
                return this.inner.getInt(random);
            }
            return this.base;
        }
        
        @Override
        public String toString() {
            return "LongVariableAmount.OptionalAmount(base=" + this.base + ", chance=" + this.chance + ", inner=" + this.inner + ")";
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