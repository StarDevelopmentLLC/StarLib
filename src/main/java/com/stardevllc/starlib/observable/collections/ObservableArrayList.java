package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents an ObservableArrayList
 *
 * @param <E> The element type
 */
public class ObservableArrayList<E> extends AbstractObservableList<E> {
    
    private final ArrayList<E> backingArrayList = new ArrayList<>();
    
    /**
     * Creates an empty observable array list
     */
    public ObservableArrayList() {
    }
    
    /**
     * Creates an observable array list from a collection
     *
     * @param collection The collection
     */
    public ObservableArrayList(Collection<E> collection) {
        backingArrayList.addAll(collection);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected List<E> getBackingList() {
        return backingArrayList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ObservableArrayList<>(this.backingArrayList.subList(fromIndex, toIndex));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> reversed() {
        return new ObservableArrayList<>(this.backingArrayList.reversed());
    }
}
