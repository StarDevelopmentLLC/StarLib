package com.stardevllc.starlib.observable.collections;

import java.util.List;

/**
 * Represents a list that can be observed for changes
 * @param <E> The element type
 */
public interface ObservableList<E> extends ObservableCollection<E>, List<E> {
}