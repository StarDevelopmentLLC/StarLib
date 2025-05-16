package com.stardevllc.observable.collections;

import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractObservableSet<E> extends AbstractObservableCollection<E> implements ObservableSet<E> {
    
    @Override
    protected Collection<E> getBackingCollection() {
        return getBackingSet();
    }
    
    protected abstract Set<E> getBackingSet();
    
    @Override
    public Iterator<E> iterator() {
        return new ObservableSetIterator<>(this, getBackingSet().iterator());
    }

    @Override
    public Spliterator<E> spliterator() {
        return getBackingSet().spliterator();
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
            backingSet.eventBus().post(new CollectionChangeEvent(backingSet, null, current));
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            backingIterator.forEachRemaining(action);
        }
    }
}
