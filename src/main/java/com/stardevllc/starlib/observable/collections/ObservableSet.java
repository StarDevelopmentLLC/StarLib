package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.collections.impl.set.ObservableHashSet;
import com.stardevllc.starlib.observable.collections.impl.set.ObservableTreeSet;
import com.stardevllc.starlib.observable.collections.listeners.SetChangeListener;

import java.util.Set;

/**
 * A set that can be observed for changes.<br>
 * Use {@link SetChangeListener} to listen to these changes. <br>
 * Use {@link ObservableHashSet} and {@link ObservableTreeSet} for the implementations of this.
 * @param <T>
 */
public interface ObservableSet<T> extends Set<T> {
    void addChangeListener(SetChangeListener<T> listener);
}
