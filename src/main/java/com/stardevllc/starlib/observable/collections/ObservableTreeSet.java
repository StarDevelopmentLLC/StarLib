package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;

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
        if (collection != null) {
            this.backingTreeSet.addAll(collection);
        }
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
        return new ObservableSetIterator<>(this, backingTreeSet.descendingIterator());
    }
    
    private class SubSetListener implements CollectionChangeListener<E> {
        @Override
        public void changed(Change<E> c) {
            if (c.added() != null) {
                add(c.added());
            } else if (c.removed() != null) {
                remove(c.removed());
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, fromInclusive, toElement, toInclusive));
        set.addListener(new SubSetListener());
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement, inclusive));
        set.addListener(new SubSetListener());
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement, inclusive));
        set.addListener(new SubSetListener());
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
        set.addListener(new SubSetListener());
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> headSet(E toElement) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement));
        set.addListener(new SubSetListener());
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<E> tailSet(E fromElement) {
        ObservableTreeSet<E> set = new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement));
        set.addListener(new SubSetListener());
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
        set.addListener(new SubSetListener());
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