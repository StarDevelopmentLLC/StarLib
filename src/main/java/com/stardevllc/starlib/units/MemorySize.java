package com.stardevllc.starlib.units;

import com.stardevllc.starlib.objects.builder.AbstractBuilder;

public record MemorySize(long bytes) implements Comparable<MemorySize> {
    
    public MemorySize(long amount, MemoryUnit unit) {
        this(unit.toBytes(amount));
    }
    
    public static MemorySize of(long amount, MemoryUnit unit) {
        return new MemorySize(amount, unit);
    }
    
    public static MemorySize of(double amount, MemoryUnit unit) {
        return new MemorySize(unit.toBytes(amount));
    }
    
    public static MemorySize of(long bytes) {
        return new MemorySize(bytes);
    }
    
    // Conversions
    public double in(MemoryUnit unit) {
        return unit.fromBytes(bytes);
    }
    
    public long inExact(MemoryUnit unit) {
        return unit.fromBytesExact(bytes);
    }
    
    // Arithmetic
    public MemorySize add(MemorySize other) {
        return new MemorySize(Math.addExact(this.bytes, other.bytes));
    }
    
    public MemorySize subtract(MemorySize other) {
        return new MemorySize(Math.subtractExact(this.bytes, other.bytes));
    }
    
    public MemorySize multiply(long factor) {
        return new MemorySize(Math.multiplyExact(this.bytes, factor));
    }
    
    @Override
    public int compareTo(MemorySize other) {
        return Long.compare(this.bytes, other.bytes);
    }
    
    public static final class Builder extends AbstractBuilder<MemorySize, Builder> {
        
        private long amount;
        private MemoryUnit unit = MemoryUnit.BYTE;
        
        public Builder() {}
        
        public Builder(Builder builder) {
            this.amount = builder.amount;
            this.unit = builder.unit;
        }
        
        public Builder(MemorySize object) {
            this.amount = object.bytes();
        }
        
        public Builder amount(long amount) {
            this.amount = amount;
            return self();
        }
        
        public Builder amount(double amount) {
            this.amount = Math.round(amount);
            return self();
        }
        
        public Builder unit(MemoryUnit unit) {
            this.unit = unit;
            return self();
        }
        
        @Override
        public MemorySize newInstance() {
            return new MemorySize(amount, unit);
        }
        
        @Override
        public Builder clone() {
            return new Builder(this);
        }
    }
}