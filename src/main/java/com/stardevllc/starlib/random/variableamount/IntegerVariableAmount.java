package com.stardevllc.starlib.random.variableamount;

import java.util.Random;

abstract class IntegerVariableAmount implements VariableAmount {
    protected static final int PRIME = 59;
    
    protected final int base;
    
    private IntegerVariableAmount(int base) {
        this.base = base;
    }
    
    @Override
    public double getDouble(Random random) {
        return getInt(random);
    }
    
    @Override
    public long getLong(Random random) {
        return getInt(random);
    }
    
    @Override
    public int hashCode() {
        return PRIME + Integer.hashCode(base);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegerVariableAmount other)) {
            return false;
        }
        return this.base == other.base;
    }
    
    static final class Fixed extends IntegerVariableAmount {
        Fixed(int base) {
            super(base);
        }
        
        @Override
        public int getInt(Random random) {
            return base;
        }
        
        @Override
        public String toString() {
            return "IntegerVariableAmount.Fixed(amount=" + this.getInt() + ")";
        }
    }
    
    static final class Range extends IntegerVariableAmount {
        private final int max;
        private final int divisor;
        
        Range(int min, int max, int divisor) {
            super(min);
            this.max = max;
            this.divisor = divisor;
        }
        
        Range(int min, int max) {
            this(min, max, 1);
        }
        
        @Override
        public int getInt(Random random) {
            return random.nextInt(this.base, this.max + 1) / divisor;
        }
        
        @Override
        public String toString() {
            return "IntegerVariableAmount.Range(min=" + this.base + ", max=" + this.max + ", divisor=" + divisor + ")";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Range other)) {
                return false;
            }
            return super.equals(o) && this.max == other.max && this.divisor == other.divisor;
        }
        
        @Override
        public int hashCode() {
            int result = super.hashCode() * PRIME + Integer.hashCode(this.max);
            return result * PRIME + Integer.hashCode(this.divisor);
        }
    }
    
    static final class BaseAndVariance extends IntegerVariableAmount {
        private final VariableAmount variance;
        private final int divisor;
        
        BaseAndVariance(int base, VariableAmount variance, int divisor) {
            super(base);
            this.variance = variance;
            this.divisor = divisor;
        }
        
        BaseAndVariance(int base, VariableAmount variance) {
            this(base, variance, 1);
        }
        
        @Override
        public int getInt(Random random) {
            int var = this.variance.getInt(random);
            return (this.base + random.nextInt(var + 1)) / divisor;
        }
        
        @Override
        public String toString() {
            return "IntegerVariableAmount.BaseAndVariance(base=" + this.base + ", variance=" + this.variance + ", divisor=" + divisor + ")";
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
            return result * PRIME + Integer.hashCode(this.divisor);
        }
    }
    
    static final class BaseAndAddition extends IntegerVariableAmount {
        private final VariableAmount addition;
        
        BaseAndAddition(int base, VariableAmount addition) {
            super(base);
            this.addition = addition;
        }
        
        @Override
        public int getInt(Random random) {
            return this.base + random.nextInt(this.addition.getInt(random));
        }
        
        @Override
        public String toString() {
            return "IntegerVariableAmount.BaseAndAddition(base=" + this.base + ", addition=" + this.addition + ")";
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
    
    static final class OptionalAmount extends IntegerVariableAmount {
        private final double chance;
        private final VariableAmount inner;
        
        OptionalAmount(int base, double chance, VariableAmount inner) {
            super(base);
            this.chance = chance;
            this.inner = inner;
        }
        
        @Override
        public int getInt(Random random) {
            if (random.nextDouble() < this.chance) {
                return this.inner.getInt(random);
            }
            return this.base;
        }
        
        @Override
        public String toString() {
            return "IntegerVariableAmount.OptionalAmount(base=" + this.base + ", chance=" + this.chance + ", inner=" + this.inner + ")";
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