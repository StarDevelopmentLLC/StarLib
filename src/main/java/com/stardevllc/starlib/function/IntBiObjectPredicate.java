package com.stardevllc.starlib.function;

@FunctionalInterface
public interface IntBiObjectPredicate<T> {
    boolean test(int i, T t1, T t2);
}