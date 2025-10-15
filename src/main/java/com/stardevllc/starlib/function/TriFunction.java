package com.stardevllc.starlib.function;

/**
 * A function that takes in three inputs and returns a single output
 *
 * @param <T> The first input type
 * @param <U> The second input type
 * @param <S> The third input type
 * @param <R> The return type
 */
@FunctionalInterface
public interface TriFunction<T, U, S, R> {
    
    /**
     * Perform the function
     *
     * @param t The first input
     * @param u The second input
     * @param s The third input
     * @return The result
     */
    R apply(T t, U u, S s);
}