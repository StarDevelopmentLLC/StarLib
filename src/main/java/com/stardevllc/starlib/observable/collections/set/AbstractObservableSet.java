package com.stardevllc.starlib.observable.collections.set;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.AbstractObservableCollection;
import com.stardevllc.starlib.observable.collections.handler.SetListenerHandler;
import com.stardevllc.starlib.observable.collections.listener.SetChangeListener;

import java.util.*;
import java.util.function.Consumer;

/**
 * An abstract version of an observable set that contains common methods
 *
 * @param <E> The element type
 */
public abstract class AbstractObservableSet<E> extends AbstractObservableCollection<E> implements ObservableSet<E> {
    
    protected final SetListenerHandler<E> handler = new SetListenerHandler<>();
    
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
    
    @Override
    public void addListener(SetChangeListener<E> changeListener) {
        handler.addListener(changeListener);
    }
    
    @Override
    public void removeListener(SetChangeListener<E> changeListener) {
        handler.removeListener(changeListener);
    }
    
    @Override
    public boolean add(E e) {
        return !handler.handleChange(this, e, null) && this.getBackingSet().add(e);
    }
    
    @Override
    public boolean remove(Object o) {
        return !handler.handleChange(this, null, (E) o) && this.getBackingSet().remove(o);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            boolean added = add(e);
            if (!modified) {
                modified = added;
            }
        }
        
        return modified;
    }
    
    public <S extends Set<E>> S addContentMirror(S set) {
        set.addAll(this);
        handler.addListener(c -> {
            if (c.added() != null) {
                set.add(c.added());
            } else if (c.removed() != null) {
                set.remove(c.removed());
            }
        });
        
        return set;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new ObservableSetIterator<>(this, handler, getBackingSet().iterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Spliterator<E> spliterator() {
        return getBackingSet().spliterator();
    }
    
    @Override
    public String toString() {
        return getBackingSet().toString();
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
        
        protected final SetListenerHandler<E> handler;
        
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
        public ObservableSetIterator(ObservableSet<E> backingSet, SetListenerHandler<E> handler, Iterator<E> backingIterator) {
            this.backingSet = backingSet;
            this.backingIterator = backingIterator;
            this.handler = handler;
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
            if (!handler.handleChange(backingSet, null, current)) {
                backingIterator.remove();
            }
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
