package com.stardevllc.observable.collections;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;
import com.stardevllc.observable.collections.listener.CollectionChangeListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractObservableCollection<E> implements ObservableCollection<E> {

    protected final EventBus<CollectionChangeEvent> eventBus = new SimpleEventBus<>();

    @Override
    public void addListener(CollectionChangeListener listener) {
        eventBus.subscribe(listener);
    }

    @Override
    public void removeListener(CollectionChangeListener listener) {
        eventBus.unsubscribe(listener);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public EventBus<CollectionChangeEvent> eventBus() {
        return eventBus;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        Iterator<E> it = iterator();

        if (o == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (E e : this) {
            action.accept(e);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = true;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
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
        return ObservableCollection.super.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
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
}