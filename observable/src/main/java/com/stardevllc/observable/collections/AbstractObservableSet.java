package com.stardevllc.observable.collections;

import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionAddEvent;
import com.stardevllc.observable.collections.event.CollectionRemoveEvent;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractObservableSet<E> extends AbstractObservableCollection<E> implements ObservableSet<E> {
    protected final Set<E> backingSet;
    
    protected AbstractObservableSet(Set<E> backingSet) {
        this.backingSet = backingSet;
    }

    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public Iterator<E> iterator() {
        return new ObservableSetIterator<>(this, backingSet.iterator());
    }

    @Override
    public Object[] toArray() {
        return backingSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingSet.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean result = this.backingSet.add(e);
        if (result) {
            this.eventBus.post(new CollectionAddEvent<>(this, e, null));
        }
        return result;
    }

    @Override
    public Stream<E> stream() {
        return backingSet.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return backingSet.parallelStream();
    }
    
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Object e : c)
                modified |= remove(e);
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }
    
    protected static class ObservableSetIterator<E> implements Observable, Iterator<E> {
        
        protected final ObservableSet<E> backingSet;
        protected final Iterator<E> backingIterator;
        
        protected E current;

        public ObservableSetIterator(ObservableSet<E> backingSet, Iterator<E> backingIterator) {
            this.backingSet = backingSet;
            this.backingIterator = backingIterator;
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
        public void remove() {
            backingIterator.remove();
            backingSet.eventBus().post(new CollectionRemoveEvent(backingSet, current));
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            backingIterator.forEachRemaining(action);
        }
    }
}
