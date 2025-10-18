package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents a TreeSet that can be observed for changes
 *
 * @param <E> The element type
 */
@SuppressWarnings("SortedCollectionWithNonComparableKeys")
public class ObservableTreeSet<E> extends AbstractObservableSet<E> implements NavigableSet<E> {
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
        this.backingTreeSet.addAll(collection);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<E> getBackingSet() {
        return backingTreeSet;
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
        this.handler.handleChange(this, null, value);
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollLast() {
        E value = backingTreeSet.pollLast();
        this.handler.handleChange(this, null, value);
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> descendingSet() {
        return new ObservableTreeSet<>(this.backingTreeSet.descendingSet());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new ObservableSetIterator<>(this, backingTreeSet.descendingIterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, fromInclusive, toElement, toInclusive));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement, inclusive));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement, inclusive));
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
        return new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, toElement));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> headSet(E toElement) {
        return new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        E removed = this.backingTreeSet.removeFirst();
        this.handler.handleChange(this, null, removed);
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        E removed = this.backingTreeSet.removeLast();
        this.handler.handleChange(this, null, removed);
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> reversed() {
        return new ObservableTreeSet<>(this.backingTreeSet.reversed());
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
        this.backingTreeSet.addFirst(e);
        this.handler.handleChange(this, e, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        this.backingTreeSet.addLast(e);
        this.handler.handleChange(this, e, null);
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