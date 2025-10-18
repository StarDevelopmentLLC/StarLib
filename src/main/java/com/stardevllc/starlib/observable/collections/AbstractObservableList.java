package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.Observable;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * An abstract class that provides common functionality for obserable lists
 *
 * @param <E> The element ty pe
 */
@SuppressWarnings("RedundantNoArgConstructor")
public abstract class AbstractObservableList<E> extends AbstractObservableCollection<E> implements ObservableList<E> {
    
    /**
     * Constructs an empty obserable list
     */
    public AbstractObservableList() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<E> getBackingCollection() {
        return getBackingList();
    }
    
    /**
     * The backing list is similar to the backing collection, should be stored the same
     *
     * @return The list that backs this collection
     */
    protected abstract List<E> getBackingList();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return getBackingList().get(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        E replaced = getBackingList().set(index, element);
        this.handler.handleChange(this, element, replaced);
        return replaced;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        getBackingList().add(index, element);
        this.handler.handleChange(this, element, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E removed = getBackingList().remove(index);
        this.handler.handleChange(this, null, removed);
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return getBackingList().indexOf(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return getBackingList().lastIndexOf(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ObservableListIterator<>(this, getBackingList().listIterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ObservableListIterator<>(this, getBackingList().listIterator(), index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(Comparator<? super E> c) {
        getBackingList().sort(c);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Spliterator<E> spliterator() {
        return getBackingList().spliterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFirst(E e) {
        this.add(0, e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addLast(E e) {
        this.add(e);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getFirst() {
        return getBackingList().getFirst();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E getLast() {
        return getBackingList().getLast();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        return this.remove(0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        return this.remove(this.size() - 1);
    }
    
    /**
     * The obserable list iterator that allows for listening to changes when using an iterator
     *
     * @param <E> The element type
     */
    protected static class ObservableListIterator<E> implements Observable, ListIterator<E> {
        
        /**
         * The backing obserable list
         */
        protected final ObservableList<E> backingList;
        
        /**
         * The backing iterator
         */
        protected final ListIterator<E> backingIterator;
        
        /**
         * The current element of the iterator
         */
        protected E current;
        
        /**
         * Constructs a new obserable list iterator
         *
         * @param backingList     The backing list
         * @param backingIterator The backing iterator
         */
        public ObservableListIterator(ObservableList<E> backingList, ListIterator<E> backingIterator) {
            this.backingList = backingList;
            this.backingIterator = backingIterator;
        }
        
        /**
         * Constructs a new obserable list iterator
         *
         * @param backingList     The backing list
         * @param backingIterator The backing iterator
         * @param startingIndex   The index to start at
         */
        public ObservableListIterator(ObservableList<E> backingList, ListIterator<E> backingIterator, int startingIndex) {
            this(backingList, backingIterator);
            for (int i = 0; i < startingIndex; i++) {
                next();
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            current = backingIterator.next();
            return current;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious() {
            return backingIterator.hasPrevious();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public E previous() {
            current = backingIterator.previous();
            return current;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int nextIndex() {
            return backingIterator.nextIndex();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int previousIndex() {
            return backingIterator.previousIndex();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            backingIterator.remove();
            backingList.getHandler().handleChange(backingList, null, current);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void set(E e) {
            backingIterator.set(e);
            backingList.getHandler().handleChange(backingList, e, current);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void add(E e) {
            backingIterator.add(e);
            backingList.getHandler().handleChange(backingList, e, null);
        }
    }
}
