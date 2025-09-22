package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.eventbus.impl.SimpleEventBus;
import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An abstract class for common elements between the different collections
 *
 * @param <E> The collection's element type
 */
@SuppressWarnings("RedundantNoArgConstructor")
public abstract class AbstractObservableCollection<E> implements ObservableCollection<E> {
    
    /**
     * Constructs an empty obserable collection
     */
    public AbstractObservableCollection() {}
    
    /**
     * An EventBus that handles change events
     */
    protected final IEventBus<CollectionChangeEvent<E>> eventBus = new SimpleEventBus<>();
    
    /**
     * The backing collection is what actually does the collection things
     *
     * @return The backing collection of this observable collection
     */
    protected abstract Collection<E> getBackingCollection();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(CollectionChangeListener<E> listener) {
        eventBus.subscribe(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(CollectionChangeListener<E> listener) {
        eventBus.unsubscribe(listener);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<E> stream() {
        return getBackingCollection().stream();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<E> parallelStream() {
        return getBackingCollection().parallelStream();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return getBackingCollection().size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new ObservableIterator<>(this, getBackingCollection().iterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return getBackingCollection().toArray();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return getBackingCollection().toArray(a);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        boolean added = getBackingCollection().add(e);
        if (added) {
            this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
        }
        return added;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getBackingCollection().isEmpty();
    }
    
    public IEventBus<CollectionChangeEvent<E>> eventBus() {
        return eventBus;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        boolean removed = getBackingCollection().remove(o);
        if (removed) {
            this.eventBus.post(new CollectionChangeEvent<>(this, null, (E) o));
        }
        
        return removed;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return getBackingCollection().contains(o);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        getBackingCollection().forEach(action);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return getBackingCollection().containsAll(c);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
                modified = true;
            }
        }
        
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Iterator<E> it = iterator();
        boolean modified = false;
        while (it.hasNext()) {
            if (filter.test(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
    
    /**
     * A basic iterator that forwards things to an actual iterator while also passing on change events in the process
     *
     * @param <E> The Element type of this collection
     */
    protected static class ObservableIterator<E> implements Iterator<E> {
        
        /**
         * The backing collection that the iterator uses
         */
        protected final ObservableCollection<E> backingCollection;
        
        /**
         * The backing iterator of this obserable iterator
         */
        protected final Iterator<E> backingIterator;
        
        /**
         * The current element that this iterator is on
         */
        protected E current;
        
        /**
         * Constructs a new obserable iterator
         *
         * @param backingCollection The backing collection
         * @param backingIterator   The backing iterator from the collection
         */
        public ObservableIterator(ObservableCollection<E> backingCollection, Iterator<E> backingIterator) {
            this.backingCollection = backingCollection;
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
            this.current = this.backingIterator.next();
            return this.current;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            this.backingIterator.remove();
            this.backingCollection.eventBus().post(new CollectionChangeEvent<>(this.backingCollection, null, current));
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            this.backingIterator.forEachRemaining(action);
        }
    }
}