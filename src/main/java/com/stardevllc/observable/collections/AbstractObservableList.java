package com.stardevllc.observable.collections;

import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;

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
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }
    
    @Override
    public E get(int index) {
        return getBackingList().get(index);
    }
    
    @Override
    public E set(int index, E element) {
        E replaced = getBackingList().set(index, element);
        this.eventBus.post(new CollectionChangeEvent<>(this, element, replaced));
        return replaced;
    }
    
    @Override
    public void add(int index, E element) {
        getBackingList().add(index, element);
        this.eventBus.post(new CollectionChangeEvent<>(this, element, null));
    }
    
    @Override
    public E remove(int index) {
        E removed = getBackingList().remove(index);
        this.eventBus.post(new CollectionChangeEvent<>(this, null, removed));
        return removed;
    }
    
    @Override
    public int indexOf(Object o) {
        return getBackingList().indexOf(o);
    }
    
    @Override
    public int lastIndexOf(Object o) {
        return getBackingList().lastIndexOf(o);
    }
    
    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return new ObservableListIterator<>(this, getBackingList().listIterator());
    }
    
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ObservableListIterator<>(this, getBackingList().listIterator(), index);
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }
    
    @Override
    public void sort(Comparator<? super E> c) {
        getBackingList().sort(c);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return getBackingList().spliterator();
    }
    
    @Override
    public void addFirst(E e) {
        this.add(0, e);
    }
    
    @Override
    public void addLast(E e) {
        this.add(e);
    }
    
    @Override
    public E getFirst() {
        return getBackingList().getFirst();
    }
    
    @Override
    public E getLast() {
        return getBackingList().getLast();
    }
    
    @Override
    public E removeFirst() {
        return this.remove(0);
    }
    
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
        
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }
        
        @Override
        public E next() {
            current = backingIterator.next();
            return current;
        }
        
        @Override
        public boolean hasPrevious() {
            return backingIterator.hasPrevious();
        }
        
        @Override
        public E previous() {
            current = backingIterator.previous();
            return current;
        }
        
        @Override
        public int nextIndex() {
            return backingIterator.nextIndex();
        }
        
        @Override
        public int previousIndex() {
            return backingIterator.previousIndex();
        }
        
        @Override
        public void remove() {
            backingIterator.remove();
            backingList.eventBus().post(new CollectionChangeEvent<>(backingList, null, current));
        }
        
        @Override
        public void set(E e) {
            backingIterator.set(e);
            backingList.eventBus().post(new CollectionChangeEvent<>(backingList, e, current));
        }
        
        @Override
        public void add(E e) {
            backingIterator.add(e);
            backingList.eventBus().post(new CollectionChangeEvent<>(backingList, e, null));
        }
    }
}
