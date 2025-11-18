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
        if (collection != null) {
            this.backingLinkedSet.addAll(collection);
        }
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
        ObservableLinkedHashSet<E> reversed = new ObservableLinkedHashSet<>(backingLinkedSet.reversed());
        reversed.addListener(c -> {
            if (c.added() != null) {
                add(c.added());
            } else if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return reversed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            backingLinkedSet.addFirst(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            backingLinkedSet.addLast(e);
        }
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
        boolean cancelled = this.handler.handleChange(this, null, removed);
        if (removed != null && cancelled) {
            backingLinkedSet.addFirst(removed);
            return null;
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        E removed = backingLinkedSet.removeLast();
        boolean cancelled = this.handler.handleChange(this, null, removed);
        if (removed != null && cancelled) {
            backingLinkedSet.addLast(removed);
            return null;
        }
        return removed;
    }
}
