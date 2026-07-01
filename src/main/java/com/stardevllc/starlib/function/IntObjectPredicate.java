package com.stardevllc.starlib.function;

@FunctionalInterface
public interface IntObjectPredicate<T> {
    void test(int i, T t);
}