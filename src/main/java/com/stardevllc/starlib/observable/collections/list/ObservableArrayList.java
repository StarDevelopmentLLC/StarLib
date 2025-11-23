package com.stardevllc.starlib.observable.collections.list;

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
        if (collection != null) {
            backingArrayList.addAll(collection);
        }
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
        ObservableArrayList<E> subList = new ObservableArrayList<>(this.backingArrayList.subList(fromIndex, toIndex));
        SubListListener listener = new SubListListener(this, subList, fromIndex, toIndex);
        subList.addListener(listener);
        this.addListener(listener);
        return subList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> reversed() {
        ObservableArrayList<E> subList = new ObservableArrayList<>(this.backingArrayList.reversed());
        SubListListener listener = new SubListListener(this, subList);
        subList.addListener(listener);
        this.addListener(listener);
        return subList;
    }
}
