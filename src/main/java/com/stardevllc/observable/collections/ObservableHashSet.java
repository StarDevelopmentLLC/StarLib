package com.stardevllc.observable.collections;

import java.util.*;

public class ObservableHashSet<E> extends AbstractObservableSet<E> {
    
    private final HashSet<E> backingHashSet = new HashSet<>();
    
    public ObservableHashSet() {
    }

    public ObservableHashSet(Collection<E> collection) {
        this.backingHashSet.addAll(collection);
    }
    
    @Override
    protected Set<E> getBackingSet() {
        return backingHashSet;
    }
}
