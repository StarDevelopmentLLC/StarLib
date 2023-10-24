package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.listeners.SetChangeListener;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ObservableSet<T> extends Iterable<T> {
    void addChangeListener(SetChangeListener<T> listener);
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
    boolean removeIf(Predicate<? super T> filter);
    Stream<T> stream();
    Stream<T> parallelStream();
    void forEach(Consumer<? super T> action);
}
