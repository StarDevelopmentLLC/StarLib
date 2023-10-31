package com.stardevllc.starlib;

/**
 * A class that just represents two values. Kind of like the Map.Entry thing, but without the hassle of doing it
 * @param firstValue The First Value
 * @param secondValue The Second Value
 * @param <K> The First Value Type
 * @param <V> The Second Value Type
 */
public record Pair<K, V>(K firstValue, V secondValue) {
}