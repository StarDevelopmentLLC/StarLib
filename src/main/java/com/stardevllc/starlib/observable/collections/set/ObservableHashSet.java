package com.stardevllc.starlib.observable.collections.set;

import java.util.*;

/**
 * Represents an ObservableHashSet
 *
 * @param <E> The element type
 */
public class ObservableHashSet<E> extends AbstractObservableSet<E> {
    
    private final HashSet<E> backingHashSet = new HashSet<>();
    
    /**
     * Creates an empty observable hash set
     */
    public ObservableHashSet() {
    }
    
    /**
     * Creates an observable hash set from a collection
     *
     * @param collection The collection
     */
    public ObservableHashSet(Collection<E> collection) {
        if (collection != null) {
            this.backingHashSet.addAll(collection);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<E> getBackingSet() {
        return backingHashSet;
    }
}
