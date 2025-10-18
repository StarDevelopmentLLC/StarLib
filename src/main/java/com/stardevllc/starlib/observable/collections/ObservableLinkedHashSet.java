package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents an ObservableLinkedHashSet
 *
 * @param <E> The element type
 */
public class ObservableLinkedHashSet<E> extends AbstractObservableSet<E> implements SequencedSet<E> {
    
    private final LinkedHashSet<E> backingLinkedSet = new LinkedHashSet<>();
    
    /**
     * Creates an empty observable linked hash set
     */
    public ObservableLinkedHashSet() {
    }
    
    /**
     * Creates an observable linked hash set from a collection
     *
     * @param collection The collection
     */
    public ObservableLinkedHashSet(Collection<E> collection) {
        this.backingLinkedSet.addAll(collection);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<E> getBackingSet() {
        return backingLinkedSet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SequencedSet<E> reversed() {
        return new ObservableLinkedHashSet<>(backingLinkedSet.reversed());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(E e) {
        backingLinkedSet.addFirst(e);
        this.handler.handleChange(this, e, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        backingLinkedSet.addLast(e);
        this.handler.handleChange(this, e, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getFirst() {
        return backingLinkedSet.getFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getLast() {
        return backingLinkedSet.getLast();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        E removed = backingLinkedSet.removeFirst();
        this.handler.handleChange(this, null, removed);
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        E removed = backingLinkedSet.removeLast();
        this.handler.handleChange(this, null, removed);
        return removed;
    }
}
