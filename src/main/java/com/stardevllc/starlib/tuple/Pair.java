package com.stardevllc.starlib.tuple;

import java.util.Map;

public interface Pair<L, R> extends Map.Entry<L, R> {
    L getLeft();
    
    R getRight();
    
    @Override
    default L getKey() {
        return getLeft();
    }
    
    @Override
    default R getValue() {
        return getRight();
    }
    
    @Override
    default R setValue(R value) {
        return null;
    }
}