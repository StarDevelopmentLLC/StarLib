package com.stardevllc.starlib.observable.collections;

import java.util.Set;

/**
 * Represents a set that can be observed for changes
 * @param <E> The element type
 */
public interface ObservableSet<E> extends ObservableCollection<E>, Set<E> {
}