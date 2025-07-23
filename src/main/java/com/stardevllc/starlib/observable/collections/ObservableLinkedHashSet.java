package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;

import java.util.*;

public class ObservableLinkedHashSet<E> extends AbstractObservableSet<E> implements SequencedSet<E> {
    
    private final LinkedHashSet<E> backingLinkedSet = new LinkedHashSet<>();
    
    public ObservableLinkedHashSet() {
    }
    
    public ObservableLinkedHashSet(Collection<E> collection) {
        this.backingLinkedSet.addAll(collection);
    }
    
    @Override
    protected Set<E> getBackingSet() {
        return backingLinkedSet;
    }
    
    @Override
    public SequencedSet<E> reversed() {
        return new ObservableLinkedHashSet<>(backingLinkedSet.reversed());
    }

    @Override
    public void addFirst(E e) {
        backingLinkedSet.addFirst(e);
        this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
    }

    @Override
    public void addLast(E e) {
        backingLinkedSet.addLast(e);
        this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
    }

    @Override
    public E getFirst() {
        return backingLinkedSet.getFirst();
    }

    @Override
    public E getLast() {
        return backingLinkedSet.getLast();
    }

    @Override
    public E removeFirst() {
        E removed = backingLinkedSet.removeFirst();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, removed));
        return removed;
    }

    @Override
    public E removeLast() {
        E removed = backingLinkedSet.removeLast();
        this.eventBus.post(new CollectionChangeEvent(this, null, removed));
        return removed;
    }
}
