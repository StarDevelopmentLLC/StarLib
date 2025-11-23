package com.stardevllc.starlib.observable.collections.set;

import java.util.*;

/**
 * Represents a TreeSet that can be observed for changes
 *
 * @param <E> The element type
 */
@SuppressWarnings("SortedCollectionWithNonComparableKeys")
public class ObservableTreeSet<E> extends AbstractObservableSequencedSet<E> implements NavigableSet<E> {
    private final TreeSet<E> backingTreeSet = new TreeSet<>();
    
    /**
     * Creates and emtpy observable tree set
     */
    public ObservableTreeSet() {
    }
    
    /**
     * Creates an observable tree set from a collection
     * @param collection The collection
     */
    public ObservableTreeSet(Collection<E> collection) {
        if (collection != null) {
            this.backingTreeSet.addAll(collection);
        }
    }
    
    @Override
    protected SequencedSet<E> getBackingSequencedSet() {
        return this.backingTreeSet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E lower(E e) {
        return backingTreeSet.lower(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E floor(E e) {
        return backingTreeSet.floor(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E ceiling(E e) {
        return backingTreeSet.ceiling(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E higher(E e) {
        return backingTreeSet.higher(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollFirst() {
        E value = backingTreeSet.pollFirst();
        boolean cancelled = this.handler.handleChange(this, null, value);
        if (value != null && cancelled) {
            backingTreeSet.addFirst(value);
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollLast() {
        E value = backingTreeSet.pollLast();
        boolean cancelled = this.handler.handleChange(this, null, value);
        if (value != null && cancelled) {
            backingTreeSet.addLast(value);
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> descendingSet() {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.descendingSet());
        set.addListener(c -> {
            if (c.added() != null) {
                add(c.added());
            } else if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new ObservableSetIterator<>(this, this.handler, backingTreeSet.descendingIterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, fromInclusive, toElement, toInclusive));
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement, inclusive));
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement, inclusive));
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<? super E> comparator() {
        return backingTreeSet.comparator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, toElement));
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> headSet(E toElement) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement));
        SubSetListener listener = new SubSetListener(this, set, null, false, toElement, false);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> tailSet(E fromElement) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement));
        SubSetListener listener = new SubSetListener(this, set, fromElement, true, null, false);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        E removed = this.backingTreeSet.removeFirst();
        boolean cancelled = this.handler.handleChange(this, null, removed);
        if (removed != null && cancelled) {
            this.backingTreeSet.addFirst(removed);
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        E removed = this.backingTreeSet.removeLast();
        boolean cancelled = this.handler.handleChange(this, null, removed);
        if (removed != null && cancelled) {
            this.backingTreeSet.addLast(removed);
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> reversed() {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.reversed());
        SubSetListener listener = new SubSetListener(this, set);
        set.addListener(listener);
        this.addListener(listener);
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E first() {
        return this.backingTreeSet.first();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E last() {
        return this.backingTreeSet.last();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            this.backingTreeSet.addFirst(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            this.backingTreeSet.addLast(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getFirst() {
        return this.backingTreeSet.getFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getLast() {
        return this.backingTreeSet.getLast();
    }
}