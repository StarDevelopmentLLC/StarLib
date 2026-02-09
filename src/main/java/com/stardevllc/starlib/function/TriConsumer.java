package com.stardevllc.starlib.function;

@FunctionalInterface
public interface TriConsumer<T, U, S> {
    void accept(T t, U u, S s);
}
