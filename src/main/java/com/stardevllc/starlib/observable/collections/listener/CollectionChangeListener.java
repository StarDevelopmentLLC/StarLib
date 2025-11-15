package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.observable.collections.ObservableCollection;

/**
 * Listener for collection change events
 *
 * @param <E> The element type
 */
@FunctionalInterface
public interface CollectionChangeListener<E> {
    
    record Change<E>(ObservableCollection<E> collection, E added, E removed) {
    }
    
    /**
     * Called when changes occur
     *
     * @param change The change information
     */
    void changed(Change<E> change);
    
    /**
     * Called when changes occur
     *
     * @param collection The collection
     * @param added      The added value (if any)
     * @param removed    The removed value (if any)
     */
    default void changed(ObservableCollection<E> collection, E added, E removed) {
        changed(new Change<>(collection, added, removed));
    }
}