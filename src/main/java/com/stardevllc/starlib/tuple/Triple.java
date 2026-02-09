package com.stardevllc.starlib.tuple;

public interface Triple<L, M, R> {
    L getLeft();
    
    M getMiddle();
    
    R getRight();
}