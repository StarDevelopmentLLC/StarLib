package com.stardevllc.random.impl;

import com.stardevllc.random.BiasedRandom;

import java.util.Random;

public class BiasedLongRandom extends LongRandom implements BiasedRandom<Long, Long> {
    
    private static final Random RANDOM = new Random();
    
    protected long biasMin, biasMax;
    protected double biasThreshold;
    
    public BiasedLongRandom(long minimum, long maximum, long biasMin, long biasMax, double biasThreshold) {
        super(minimum, maximum);
        this.biasMin = biasMin;
        this.biasMax = biasMax;
        this.biasThreshold = biasThreshold;
    }

    @Override
    public Long generate(Object... options) {
        if (biasThreshold > 0.0) {
            if (biasMin < minimum) {
                throw new IllegalArgumentException("biasMin must be greater than or equal to min");
            }

            if (biasMax > maximum) {
                throw new IllegalArgumentException("biaMax must be less than or equal to max");
            }

            double biasChance = RANDOM.nextDouble();
            if (biasChance < biasThreshold) {
                if (biasMin == biasMax) {
                    return biasMin;
                }

                return RANDOM.nextLong(biasMin, biasMax + 1);
            } else {
                return RANDOM.nextLong(minimum, maximum + 1);
            }
        } else {
            return RANDOM.nextLong(minimum, maximum + 1);
        }
    }

    @Override
    public Long getBiasMinimum() {
        return biasMin;
    }

    @Override
    public Long getBiasMaximum() {
        return biasMax;
    }

    @Override
    public double getBiasThreshold() {
        return biasThreshold;
    }

    public long getBiasMin() {
        return biasMin;
    }

    public long getBiasMax() {
        return biasMax;
    }

    public void setBiasMin(long biasMin) {
        this.biasMin = biasMin;
    }

    public void setGetBiasThreshold(double getBiasThreshold) {
        this.biasThreshold = getBiasThreshold;
    }

    public void setBiasMax(long biasMax) {
        this.biasMax = biasMax;
    }

    @Override
    public BiasedLongRandom clone() {
        return new BiasedLongRandom(minimum, maximum, biasMin, biasMax, biasThreshold);
    }
}
