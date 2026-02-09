package com.stardevllc.starlib.tuple;

import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class ImmutablePair<L, R> implements Pair<L, R> {
    
    private final L left;
    private final R right;
    
    public ImmutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public ImmutablePair() {
        this(null, null);
    }
    
    @Override
    public L getLeft() {
        return left;
    }
    
    @Override
    public R getRight() {
        return right;
    }
    
    @Override
    public R setValue(R value) {
        throw new UnsupportedOperationException("Cannot set the value of an immutable pair");
    }
    
    public static <L, R> Pair<L, R> left(L left) {
        return new ImmutablePair<>(left, null);
    }
    
    public static <L, R> Pair<L, R> right(R right) {
        return new ImmutablePair<>(null, right);
    }
    
    public static <L, R> ImmutablePair<L, R> of(L left, R right) {
        return new ImmutablePair<>(left, right);
    }
    
    public static <L, R> ImmutablePair<L, R> of(Map.Entry<L, R> pair) {
        return new ImmutablePair<>(pair.getKey(), pair.getValue());
    }
}
