package com.stardevllc.observable.collections;

import com.stardevllc.observable.collections.event.CollectionChangeEvent;

import java.util.*;

@SuppressWarnings("SortedCollectionWithNonComparableKeys")
public class ObservableTreeSet<E> extends AbstractObservableSet<E> implements NavigableSet<E> {
    protected final TreeSet<E> backingTreeSet = new TreeSet<>();
    
    public ObservableTreeSet() {
    }
    
    public ObservableTreeSet(Collection<E> collection) {
        this.backingTreeSet.addAll(collection);
    }
    
    @Override
    protected Set<E> getBackingSet() {
        return backingTreeSet;
    }
    
    @Override
    public E lower(E e) {
        return backingTreeSet.lower(e);
    }

    @Override
    public E floor(E e) {
        return backingTreeSet.floor(e);
    }

    @Override
    public E ceiling(E e) {
        return backingTreeSet.ceiling(e);
    }

    @Override
    public E higher(E e) {
        return backingTreeSet.higher(e);
    }

    @Override
    public E pollFirst() {
        E value = backingTreeSet.pollFirst();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, value));
        return value;
    }

    @Override
    public E pollLast() {
        E value = backingTreeSet.pollLast();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, value));
        return value;
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ObservableTreeSet<>(this.backingTreeSet.descendingSet());
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new ObservableSetIterator<>(this, backingTreeSet.descendingIterator());
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, fromInclusive, toElement, toInclusive));
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement, inclusive));
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement, inclusive));
    }

    @Override
    public Comparator<? super E> comparator() {
        return backingTreeSet.comparator();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return new ObservableTreeSet<>(this.backingTreeSet.subSet(fromElement, toElement));
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return new ObservableTreeSet<>(this.backingTreeSet.headSet(toElement));
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return new ObservableTreeSet<>(this.backingTreeSet.tailSet(fromElement));
    }

    @Override
    public E removeFirst() {
        E removed = this.backingTreeSet.removeFirst();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, removed));
        return removed;
    }

    @Override
    public E removeLast() {
        E removed = this.backingTreeSet.removeLast();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, removed));
        return removed;
    }

    @Override
    public NavigableSet<E> reversed() {
        return new ObservableTreeSet<>(this.backingTreeSet.reversed());
    }

    @Override
    public E first() {
        return this.backingTreeSet.first();
    }

    @Override
    public E last() {
        return this.backingTreeSet.last();
    }

    @Override
    public void addFirst(E e) {
        this.backingTreeSet.addFirst(e);
        this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
    }

    @Override
    public void addLast(E e) {
        this.backingTreeSet.addLast(e);
        this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
    }

    @Override
    public E getFirst() {
        return this.backingTreeSet.getFirst();
    }

    @Override
    public E getLast() {
        return this.backingTreeSet.getLast();
    }
}
