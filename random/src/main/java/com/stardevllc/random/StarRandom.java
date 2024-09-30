package com.stardevllc.random;

/**
 * Parent interface for all custom random definitions
 * @param <T> The type of the min and max numbers. Child classes will have a getMin() and getMax() for the primitives
 * @param <V> The value represented by the random.
 */
public interface StarRandom<T extends Number, V> {
    V generate(Object... options);
    T getMinimum();
    T getMaximum();
    void setMinimum(T minimum);
    void setMaximum(T maximum);
}
