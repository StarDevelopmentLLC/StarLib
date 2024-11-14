package com.stardevllc.observable.collections;

import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.SequencedSet;

public class ObservableLinkedHashSet<E> extends AbstractObservableSet<E> implements SequencedSet<E> {
    
    private final LinkedHashSet<E> backingLinkedSet;
    
    public ObservableLinkedHashSet() {
        super(new LinkedHashSet<>());
        this.backingLinkedSet = (LinkedHashSet<E>) this.backingSet;
    }
    
    public ObservableLinkedHashSet(Collection<E> collection) {
        this();
        this.backingSet.addAll(collection);
    }

    @Override
    public SequencedSet<E> reversed() {
        return new ObservableLinkedHashSet<>(backingLinkedSet.reversed());
    }

    @Override
    public void addFirst(E e) {
        backingLinkedSet.addFirst(e);
        this.eventBus.post(new CollectionAddEvent<>(this, e, null));
    }

    @Override
    public void addLast(E e) {
        backingLinkedSet.addLast(e);
        this.eventBus.post(new CollectionAddEvent<>(this, e, null));
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
        this.eventBus.post(new CollectionRemoveEvent(this, removed));
        return removed;
    }

    @Override
    public E removeLast() {
        E removed = backingLinkedSet.removeLast();
        this.eventBus.post(new CollectionRemoveEvent(this, removed));
        return removed;
    }
}
