package com.stardevllc.starlib.tuple;

public class MutableEither<L, R> implements Either<L, R> {
    
    public static <L, R> MutableEither<L, R> left(L left) {
        return new MutableEither<>(left, null);
    }
    
    public static <L, R> MutableEither<L, R> right(R right) {
        return new MutableEither<>(null, right);
    }
    
    protected L left;
    protected R right;
    
    public MutableEither(L left, R right) {
        if (left != null && right != null) {
            throw new IllegalStateException("Left and Right cannot both have values");
        }
        
        this.left = left;
        this.right = right;
    }
    
    @Override
    public L getLeft() {
        return left;
    }
    
    public void setLeft(L left) {
        this.left = left;
        this.right = null;
    }
    
    @Override
    public R getRight() {
        return right;
    }
    
    public void setRight(R right) {
        this.right = right;
        this.left = null;
    }
}