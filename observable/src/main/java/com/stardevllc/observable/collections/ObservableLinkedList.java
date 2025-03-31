package com.stardevllc.observable.collections;

import com.stardevllc.observable.collections.event.CollectionChangeEvent;

import java.util.*;

public class ObservableLinkedList<E> extends AbstractObservableList<E> implements Deque<E> {

    protected final LinkedList<E> backingLinkedList;

    public ObservableLinkedList() {
        super(new LinkedList<>());
        this.backingLinkedList = (LinkedList<E>) this.backingList;
    }

    public ObservableLinkedList(Collection<E> collection) {
        this();
        this.backingList.addAll(collection);
    }

    public ObservableLinkedList<E> reversed() {
        return new ObservableLinkedList<>(this.backingLinkedList.reversed());
    }

    @Override
    public boolean offerFirst(E e) {
        boolean result = this.backingLinkedList.offerFirst(e);
        if (result) {
            this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
        }
        return result;
    }

    @Override
    public boolean offerLast(E e) {
        boolean result = this.backingLinkedList.offerLast(e);
        if (result) {
            this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
        }
        return result;
    }

    @Override
    public E pollFirst() {
        E value = this.backingLinkedList.pollFirst();
        this.eventBus.post(new CollectionChangeEvent(this, null, value));
        return value;
    }

    @Override
    public E pollLast() {
        E value = this.backingLinkedList.pollLast();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, value));
        return value;
    }

    @Override
    public E peekFirst() {
        return this.backingLinkedList.peekFirst();
    }

    @Override
    public E peekLast() {
        return this.backingLinkedList.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        boolean result = this.backingLinkedList.removeFirstOccurrence(o);
        if (result) {
            this.eventBus.post(new CollectionChangeEvent(this, null, o));
        }
        return result;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        boolean result = this.backingLinkedList.removeLastOccurrence(o);
        if (result) {
            this.eventBus.post(new CollectionChangeEvent<>(this, null, (E) o));
        }
        return result;
    }

    @Override
    public boolean offer(E e) {
        boolean result = this.backingLinkedList.offer(e);
        if (result) {
            this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
        }
        return result;
    }

    @Override
    public E remove() {
        E removed = this.backingLinkedList.remove();
        this.eventBus.post(new CollectionChangeEvent(this, null, removed));
        return removed;
    }

    @Override
    public E poll() {
        E value = this.backingLinkedList.poll();
        this.eventBus.post(new CollectionChangeEvent<>(this, null, value));
        return value;
    }

    @Override
    public E element() {
        return this.backingLinkedList.element();
    }

    @Override
    public E peek() {
        return this.backingLinkedList.peek();
    }

    @Override
    public void push(E e) {
        this.backingLinkedList.push(e);
        this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new ObservableIterator<>(this, this.backingLinkedList.descendingIterator());
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ObservableLinkedList<>(this.backingLinkedList.subList(fromIndex, toIndex));
    }
}
