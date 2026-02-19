package com.stardevllc.starlib.collections;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class AddOnlyArrayList<E> extends ArrayList<E> {
    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot clear elements in an AddOnlyArrayList");
    }
    
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException("Cannot remove elements in an AddOnlyArrayList");
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Cannot create a sublist in an AddOnlyArrayList");
    }
}