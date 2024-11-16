package com.stardevllc.observable.collections;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;
import com.stardevllc.observable.collections.listener.CollectionChangeListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractObservableCollection<E> implements ObservableCollection<E> {

    protected final Collection<E> backingCollection;
    protected final EventBus<CollectionChangeEvent<E>> eventBus = new SimpleEventBus<>();
    
    public AbstractObservableCollection(Collection<E> collection) {
        this.backingCollection = collection;
    }

    @Override
    public void addListener(CollectionChangeListener listener) {
        eventBus.subscribe(listener);
    }

    @Override
    public void removeListener(CollectionChangeListener listener) {
        eventBus.unsubscribe(listener);
    }

    @Override
    public Stream<E> stream() {
        return this.backingCollection.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return this.backingCollection.parallelStream();
    }

    @Override
    public int size() {
        return this.backingCollection.size();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return this.backingCollection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingCollection.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean added = this.backingCollection.add(e);
        if (added) {
            this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
        }
        return added;
    }

    @Override
    public boolean isEmpty() {
        return this.backingCollection.isEmpty();
    }
    
    public EventBus<CollectionChangeEvent<E>> eventBus() {
        return eventBus;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = this.backingCollection.remove(o);
        if (removed) {
            this.eventBus.post(new CollectionChangeEvent<>(this, null, (E) o));
        }
        
        return removed;
    }

    @Override
    public boolean contains(Object o) {
        return this.backingCollection.contains(o);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        this.backingCollection.forEach(action);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingCollection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = true;
        for (E e : c) {
            if (add(e)) {
                this.eventBus.post(new CollectionChangeEvent<>(this, e, null));
                modified = true;
            }
        }

        return modified;
    }

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

    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
    
    protected static class ObservableIterator<E> implements Iterator<E> {
        
        protected final ObservableCollection<E> backingCollection;
        protected final Iterator<E> backingIterator;
        
        protected E current;

        public ObservableIterator(ObservableCollection<E> backingCollection, Iterator<E> backingIterator) {
            this.backingCollection = backingCollection;
            this.backingIterator = backingIterator;
        }

        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }

        @Override
        public E next() {
            this.current = this.backingIterator.next();
            return this.current;
        }

        @Override
        public void remove() {
            this.backingIterator.remove();
            this.backingCollection.eventBus().post(new CollectionChangeEvent(this.backingCollection, null, current));
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            this.backingIterator.forEachRemaining(action);
        }
    }
}