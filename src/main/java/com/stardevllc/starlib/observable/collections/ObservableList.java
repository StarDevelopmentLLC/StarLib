package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.impl.list.ObservableArrayList;
import com.stardevllc.starlib.observable.collections.impl.list.ObservableLinkedList;
import com.stardevllc.starlib.observable.collections.listeners.ListChangeListener;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A list that can be observed for changes.<br>
 * Use {@link ListChangeListener} to listen to these changes. <br>
 * Use {@link ObservableArrayList} and {@link ObservableLinkedList} for the implementations of this.
 * @param <T>
 */
public interface ObservableList<T> extends Iterable<T> {
    void addChangeListener(ListChangeListener<T> listener);
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Object[] toArray();
    <T1> T1[] toArray(T1[] a);
    boolean add(T t);
    boolean remove(Object o);
    boolean addAll(Collection<? extends T> c);
    boolean removeAll(Collection<?> c);
    void clear();
    T get(int index);
    T set(int index, T element);
    void add(int index, T element);
    T remove(int index);
    int indexOf(Object o);
    int lastIndexOf(Object o);
    void forEach(Consumer<? super T> action);
    Stream<T> stream();
    Stream<T> parallelStream();
}