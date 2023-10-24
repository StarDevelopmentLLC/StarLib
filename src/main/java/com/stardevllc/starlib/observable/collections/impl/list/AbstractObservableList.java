package com.stardevllc.starlib.observable.collections.impl.list;

import com.stardevllc.starlib.observable.collections.ObservableList;
import com.stardevllc.starlib.observable.collections.listeners.ListChangeListener;
import com.stardevllc.starlib.observable.collections.listeners.ListChangeListener.Change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractObservableList<T> implements ObservableList<T> {

    protected List<T> value;
    protected ArrayList<ListChangeListener<T>> changeListeners = new ArrayList<>();
    
    public AbstractObservableList(List<T> list) {
        this.value = list;
    }

    @Override
    public void addChangeListener(ListChangeListener<T> listener) {
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
    public Iterator<T> iterator() {
        return value.iterator();
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
        Change<T> change = new Change<>(this);
        change.getAdded().add(t);
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return true;
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
    public T get(int index) {
        return this.value.get(index);
    }

    @Override
    public T set(int index, T element) {
        T old = this.value.set(index, element);
        Change<T> change = new Change<>(this);
        change.getAdded().add(element);
        if (old != null) {
            change.getRemoved().add(old);
        }
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return old;
    }

    @Override
    public void add(int index, T element) {
        this.value.add(index, element);
        Change<T> change = new Change<>(this);
        change.getAdded().add(element);
        this.changeListeners.forEach(listener -> listener.onChange(change));
    }

    @Override
    public T remove(int index) {
        T element = this.value.remove(index);
        Change<T> change = new Change<>(this);
        change.getRemoved().add(element);
        this.changeListeners.forEach(listener -> listener.onChange(change));
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return this.value.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.value.lastIndexOf(o);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.value.forEach(action);
    }

    @Override
    public Stream<T> stream() {
        return value.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return value.parallelStream();
    }
}
