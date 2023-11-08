package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.impl.list.ObservableArrayList;
import com.stardevllc.starlib.observable.collections.impl.list.ObservableLinkedList;
import com.stardevllc.starlib.observable.collections.listeners.ListChangeListener;

import java.util.List;

/**
 * A list that can be observed for changes.<br>
 * Use {@link ListChangeListener} to listen to these changes. <br>
 * Use {@link ObservableArrayList} and {@link ObservableLinkedList} for the implementations of this.
 * @param <T>
 */
public interface ObservableList<T> extends List<T> {
    void addChangeListener(ListChangeListener<T> listener);
}