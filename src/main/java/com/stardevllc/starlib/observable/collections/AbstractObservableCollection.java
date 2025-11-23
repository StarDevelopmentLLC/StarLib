package com.stardevllc.starlib.observable.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.*;
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
    public AbstractObservableCollection() {
    }
    
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
    
    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return ObservableCollection.super.toArray(generator);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getBackingCollection().isEmpty();
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
}