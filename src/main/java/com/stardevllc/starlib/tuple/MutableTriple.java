package com.stardevllc.starlib.tuple;

public class MutableTriple<L, M, R> implements Triple<L, M, R> {
    
    private L left;
    private M middle;
    private R right;
    
    public MutableTriple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public MutableTriple() {}
    
    @Override
    public L getLeft() {
        return left;
    }
    
    public void setLeft(L left) {
        this.left = left;
    }
    
    @Override
    public M getMiddle() {
        return middle;
    }
    
    public void setMiddle(M middle) {
        this.middle = middle;
    }
    
    @Override
    public R getRight() {
        return right;
    }
    
    public void setRight(R right) {
        this.right = right;
    }
    
    public static <L, M, R> MutableTriple<L, M, R> of(L left, M middle, R right) {
        return new MutableTriple<>(left, middle, right);
    }
}
