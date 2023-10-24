package com.stardevllc.starlib.observable.collections.impl.set;

import com.stardevllc.starlib.observable.collections.ObservableSet;
import com.stardevllc.starlib.observable.collections.listeners.SetChangeListener;
import com.stardevllc.starlib.observable.collections.listeners.SetChangeListener.Change;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractObservableSet<T> implements ObservableSet<T> {
    
    protected Set<T> value;
    protected ArrayList<SetChangeListener<T>> changeListeners = new ArrayList<>();

    public AbstractObservableSet(Set<T> value) {
        this.value = value;
    }

    @Override
    public void addChangeListener(SetChangeListener<T> listener) {
        this.changeListeners.add(listener);
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return value.contains(o);
    }

    @Override
    public Object[] toArray() {
        return value.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return value.toArray(a);
    }

    @Override
    public boolean add(T t) {
        boolean result = value.add(t);
        if (result) {
            Change<T> change = new Change<>(this);
            change.getAdded().add(t);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = value.remove(o);
        if (result) {
            Change<T> change = new Change<>(this);
            change.getRemoved().add((T) o);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = value.addAll(c);
        if (result) {
            Change<T> change = new Change<>(this);
            change.getAdded().addAll(c);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = value.removeAll(c);
        if (result) {
            Change<T> change = new Change<>(this);
            change.getRemoved().addAll((Collection<? extends T>) c);
            this.changeListeners.forEach(listener -> listener.onChange(change));
        }
        return false;
    }

    @Override
    public void clear() {
        Change<T> change = new Change<>(this);
        change.getRemoved().addAll(value);
        this.changeListeners.forEach(listener -> listener.onChange(change));
        this.value.clear();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        Change<T> change = new Change<>(this);
        boolean removed = false;
        final Iterator<T> each = iterator();
        while (each.hasNext()) {
            T next = each.next();
            if (filter.test(next)) {
                change.getRemoved().add(next);
                each.remove();
                removed = true;
            }
        }
        
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return removed;
    }

    @Override
    public Stream<T> stream() {
        return this.value.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return this.value.parallelStream();
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.value.forEach(action);
    }
}
