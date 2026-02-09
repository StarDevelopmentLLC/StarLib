package com.stardevllc.starlib.tuple;

@SuppressWarnings("ClassCanBeRecord")
public class ImmutableTriple<L, M, R> implements Triple<L, M, R> {
    
    private final L left;
    private final M middle;
    private final R right;
    
    public ImmutableTriple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public ImmutableTriple() {
        this(null, null, null);
    }
    
    @Override
    public L getLeft() {
        return left;
    }
    
    @Override
    public M getMiddle() {
        return middle;
    }
    
    @Override
    public R getRight() {
        return right;
    }
    
    public static <L, M, R> ImmutableTriple<L, M, R> of(L left, M middle, R right) {
        return new ImmutableTriple<>(left, middle, right);
    }
}
