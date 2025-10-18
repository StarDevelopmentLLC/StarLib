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
        this.backingLinkedList.addAll(collection);
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
        return new ObservableLinkedList<>(this.backingLinkedList.reversed());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offerFirst(E e) {
        boolean result = this.backingLinkedList.offerFirst(e);
        if (result) {
            this.handler.handleChange(this, e, null);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offerLast(E e) {
        boolean result = this.backingLinkedList.offerLast(e);
        if (result) {
            this.handler.handleChange(this, e, null);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollFirst() {
        E value = this.backingLinkedList.pollFirst();
        this.handler.handleChange(this, null, value);
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E pollLast() {
        E value = this.backingLinkedList.pollLast();
        this.handler.handleChange(this, null, value);
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
        boolean result = this.backingLinkedList.removeFirstOccurrence(o);
        if (result) {
            this.handler.handleChange(this, null, (E) o);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeLastOccurrence(Object o) {
        boolean result = this.backingLinkedList.removeLastOccurrence(o);
        if (result) {
            this.handler.handleChange(this, null, (E) o);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean offer(E e) {
        boolean result = this.backingLinkedList.offer(e);
        if (result) {
            this.handler.handleChange(this, e, null);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E remove() {
        E removed = this.backingLinkedList.remove();
        this.handler.handleChange(this, null, removed);
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E poll() {
        E value = this.backingLinkedList.poll();
        this.handler.handleChange(this, null, value);
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
        this.backingLinkedList.push(e);
        this.handler.handleChange(this, e, null);
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
        return new ObservableLinkedList<>(this.backingLinkedList.subList(fromIndex, toIndex));
    }
}