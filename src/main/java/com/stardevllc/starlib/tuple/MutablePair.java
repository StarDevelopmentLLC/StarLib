package com.stardevllc.starlib.tuple;

import java.util.Map;

public class MutablePair<L, R> implements Pair<L, R> {
    
    private L left;
    private R right;
    
    public MutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public MutablePair() {
    }
    
    @Override
    public L getLeft() {
        return left;
    }
    
    public void setLeft(L left) {
        this.left = left;
    }
    
    @Override
    public R getRight() {
        return right;
    }
    
    public void setRight(R right) {
        this.right = right;
    }
    
    @Override
    public R setValue(R value) {
        setRight(value);
        return value;
    }
    
    public static <L, R> Pair<L, R> left(L left) {
        return new MutablePair<>(left, null);
    }
    
    public static <L, R> Pair<L, R> right(R right) {
        return new MutablePair<>(null, right);
    }
    
    public static <L, R> MutablePair<L, R> of(L left, R right) {
        return new MutablePair<>(left, right);
    }
    
    public static <L, R> MutablePair<L, R> of(Map.Entry<L, R> pair) {
        return new MutablePair<>(pair.getKey(), pair.getValue());
    }
}
