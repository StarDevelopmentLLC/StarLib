package com.stardevllc.starlib.tuple;

@SuppressWarnings("ClassCanBeRecord")
public final class ImmutableEither<L, R> implements Either<L, R> {
    
    public static <L, R> ImmutableEither<L, R> left(L left) {
        return new ImmutableEither<>(left, null);
    }
    
    public static <L, R> ImmutableEither<L, R> right(R right) {
        return new ImmutableEither<>(null, right);
    }
    
    private final L left;
    private final R right;
    
    public ImmutableEither(L left, R right) {
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
    
    @Override
    public R getRight() {
        return right;
    }
}