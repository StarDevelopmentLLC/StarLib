package com.stardevllc.random;

public interface BiasedRandom<T, V> extends StarRandom<T, V> {
    T getBiasMinimum();
    T getBiasMaximum();
    double getBiasThreshold();
}