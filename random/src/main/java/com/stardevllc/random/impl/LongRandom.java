package com.stardevllc.random.impl;

import com.stardevllc.random.StarRandom;

import java.util.Random;

public class LongRandom implements StarRandom<Long, Long> {
    
    private static final Random RANDOM = new Random();
    
    protected long minimum, maximum;

    public LongRandom(long minimum, long maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public Long generate(Object... options) {
        return RANDOM.nextLong(minimum, maximum + 1);
    }

    @Override
    public Long getMinimum() {
        return this.minimum;
    }

    @Override
    public Long getMaximum() {
        return maximum;
    }

    @Override
    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    @Override
    public void setMaximum(Long maximum) {
        this.maximum = maximum;
    }

    public long getMin() {
        return this.minimum;
    }
    
    public long getMax() {
        return this.maximum;
    }
    
    public void setMin(long minimum) {
        this.minimum = minimum;
    }
    
    public void setMax(long maximum) {
        this.maximum = maximum;
    }
}
