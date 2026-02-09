package com.stardevllc.starlib.helper;

/**
 * Represents a set of two values
 * @param first The first value
 * @param second The second value
 * @param <F> First type
 * @param <S> Second type
 */
@Deprecated(since = "0.23.2")
public record Pair<F, S>(F first, S second) {
}