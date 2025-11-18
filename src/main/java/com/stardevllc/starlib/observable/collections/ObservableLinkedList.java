package com.stardevllc.starlib.observable.collections;

import java.util.*;

/**
 * Represents an ObservableLinkedList
 *
 * @param <E> The element type
 */
public class ObservableLinkedList<E> extends AbstractObservableList<E> implements Deque<E> {
    
    private final LinkedList<E> backingLinkedList = new LinkedList<>();
    
    /**
     * Creates an empty observable linked list
     */
    public ObservableLinkedList() {
    }
    
    /**
     * Creates an observable linked list from a collection
     *
     * @param collection The collection
     */
    public ObservableLinkedList(Collection<E> collection) {
        if (collection != null) {
            this.backingLinkedList.addAll(collection);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected List<E> getBackingList() {
        return backingLinkedList;
    }
    
    /**
     * {@inheritDoc}
     */
    public ObservableLinkedList<E> reversed() {
        ObservableLinkedList<E> reversed = new ObservableLinkedList<>(this.backingLinkedList.reversed());
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
    public boolean offerFirst(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            return this.backingLinkedList.offerFirst(e);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offerLast(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            return this.backingLinkedList.offerLast(e);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollFirst() {
        E value = this.backingLinkedList.pollFirst();
        boolean cancelled = this.handler.handleChange(this, null, value);
        if (value != null && cancelled) {
            this.backingLinkedList.addFirst(value);
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollLast() {
        E value = this.backingLinkedList.pollLast();
        boolean cancelled = this.handler.handleChange(this, null, value);
        if (value != null && cancelled) {
            this.backingLinkedList.addLast(value);
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E peekFirst() {
        return this.backingLinkedList.peekFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E peekLast() {
        return this.backingLinkedList.peekLast();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (!this.handler.handleChange(this, null, (E) o)) {
            return this.backingLinkedList.removeFirstOccurrence(o);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeLastOccurrence(Object o) {
        if (!this.handler.handleChange(this, null, (E) o)) {
            return this.backingLinkedList.removeLastOccurrence(o);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offer(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            return this.backingLinkedList.offer(e);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E remove() {
        E removed = this.backingLinkedList.remove();
        boolean cancelled = this.handler.handleChange(this, null, removed);
        if (removed != null && cancelled) {
            this.backingLinkedList.add(removed);
        }
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E poll() {
        E value = this.backingLinkedList.poll();
        boolean cancelled = this.handler.handleChange(this, null, value);
        if (value != null && cancelled) {
            this.backingLinkedList.add(value);
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E element() {
        return this.backingLinkedList.element();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E peek() {
        return this.backingLinkedList.peek();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void push(E e) {
        if (!this.handler.handleChange(this, e, null)) {
            this.backingLinkedList.push(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pop() {
        return removeFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new ObservableIterator<>(this, this.backingLinkedList.descendingIterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        ObservableLinkedList<E> subList = new ObservableLinkedList<>(this.backingLinkedList.subList(fromIndex, toIndex));
        subList.addListener(c -> {
            if (c.added() != null) {
                add(c.added());
            } else if (c.removed() != null) {
                remove(c.removed());
            }
        });
        return subList;
    }
}