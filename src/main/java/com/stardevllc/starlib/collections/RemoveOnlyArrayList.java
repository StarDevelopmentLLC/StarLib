package com.stardevllc.starlib.collections;

import java.util.*;
import java.util.function.UnaryOperator;

public class RemoveOnlyArrayList<E> extends ArrayList<E> {
    public RemoveOnlyArrayList(int initialCapacity) {
        super(initialCapacity);
    }
    
    public RemoveOnlyArrayList() {
    }
    
    public RemoveOnlyArrayList(Collection<? extends E> c) {
        super(c);
    }
    
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Cannot add elements to a RemoveOnlyArrayList");
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Cannot add elements to a RemoveOnlyArrayList");
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Cannot add elements to a RemoveOnlyArrayList");
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException("Cannot replace elements to a RemoveOnlyArrayList");
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Cannot create a sublist in a RemoveOnlyArrayList");
    }
}