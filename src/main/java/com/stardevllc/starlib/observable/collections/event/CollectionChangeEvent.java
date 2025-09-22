package com.stardevllc.starlib.observable.collections.event;

import com.stardevllc.starlib.observable.collections.ObservableCollection;

/**
 * Event for when a collection changes
 *
 * @param collection The collection
 * @param added      Element added (can be null)
 * @param removed    Element removed (can be null)
 * @param <E>        Element type
 */
public record CollectionChangeEvent<E>(ObservableCollection<E> collection, E added, E removed) {
}
