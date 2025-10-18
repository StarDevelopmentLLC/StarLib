package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.observable.collections.ObservableCollection;

/**
 * Listener for collection change events
 *
 * @param <E> The element type
 */
@FunctionalInterface
public interface CollectionChangeListener<E> {
    
    /**
     * Called when changes occur
     *
     * @param collection The collection that changed
     * @param added      The element added
     * @param removed    The element removed
     */
    void changed(ObservableCollection<E> collection, E added, E removed);
}