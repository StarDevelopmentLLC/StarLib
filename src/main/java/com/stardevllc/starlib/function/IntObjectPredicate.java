package com.stardevllc.starlib.function;

@FunctionalInterface
public interface IntObjectPredicate<T> {
    boolean test(int i, T t);
}