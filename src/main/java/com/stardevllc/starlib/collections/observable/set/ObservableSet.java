package com.stardevllc.starlib.collections.observable.set;

import com.stardevllc.starlib.collections.observable.ObservableCollection;
import com.stardevllc.starlib.collections.observable.listener.SetChangeListener;

import java.util.Set;

/**
 * Represents a set that can be observed for changes
 *
 * @param <E> The element type
 */
public interface ObservableSet<E> extends ObservableCollection<E>, Set<E> {
    
    void addListener(SetChangeListener<E> changeListener);
    
    void removeListener(SetChangeListener<E> changeListener);
    
    /**
     * Adds a listener that mirrors changes in this observable set to the set passed in. <br>
     *
     * @param set The set to mirror changes into
     * @return The same set passed in
     */
    <S extends Set<E>> S addContentMirror(S set);
}