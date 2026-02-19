package com.stardevllc.starlib.tuple;

import java.util.function.Consumer;

public interface Either<L, R> {
    L getLeft();
    
    default boolean isLeftPresent() {
        return getLeft() != null;
    }
    
    default void ifLeftPresent(Consumer<L> consumer) {
        if (getLeft() != null) {
            consumer.accept(getLeft());
        }
    }
    
    R getRight();
    
    default boolean isRightPresent() {
        return getRight() != null;
    }
    
    default void ifRightPresent(Consumer<R> consumer) {
        if (getRight() != null) {
            consumer.accept(getRight());
        }
    }
}