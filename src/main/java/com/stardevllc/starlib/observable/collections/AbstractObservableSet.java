package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;

import java.util.*;
import java.util.function.Consumer;

/**
 * An abstract version of an observable set that contains common methods
 *
 * @param <E> The element type
 */
public abstract class AbstractObservableSet<E> extends AbstractObservableCollection<E> implements ObservableSet<E> {
    
    /**
     * Constructs an empty ObservableSet
     */
    public AbstractObservableSet() {
    }
    
    /**
     * Constructs a new ObservableSet that has elements from an existing set
     *
     * @param set The set that contains existing elements
     */
    public AbstractObservableSet(Set<E> set) {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<E> getBackingCollection() {
        return getBackingSet();
    }
    
    /**
     * The Backing Set of this Observable Set
     *
     * @return The backing set
     */
    protected abstract Set<E> getBackingSet();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new ObservableSetIterator<>(this, getBackingSet().iterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Spliterator<E> spliterator() {
        return getBackingSet().spliterator();
    }
    
    /**
     * A custom iterator instance that allows detection of changes
     *
     * @param <E> The element type
     */
    protected static class ObservableSetIterator<E> implements Observable, Iterator<E> {
        
        /**
         * The backing ObservableSet
         */
        protected final ObservableSet<E> backingSet;
        
        /**
         * The backing Iterator
         */
        protected final Iterator<E> backingIterator;
        
        /**
         * The current value of the iterator
         */
        protected E current;
        
        /**
         * Constructs a new Observable Set Iterator
         *
         * @param backingSet      The backing observable set
         * @param backingIterator The backing iterator
         */
        public ObservableSetIterator(ObservableSet<E> backingSet, Iterator<E> backingIterator) {
            this.backingSet = backingSet;
            this.backingIterator = backingIterator;
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
        public void remove() {
            backingIterator.remove();
            backingSet.eventBus().post(new CollectionChangeEvent<>(backingSet, null, current));
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            backingIterator.forEachRemaining(action);
        }
    }
}
