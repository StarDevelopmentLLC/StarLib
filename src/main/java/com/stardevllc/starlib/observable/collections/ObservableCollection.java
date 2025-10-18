package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.handler.CollectionListenerHandler;
import com.stardevllc.starlib.observable.collections.listener.CollectionChangeListener;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Represents an collection that can be observed for changes
 *
 * @param <E> The element type
 */
public interface ObservableCollection<E> extends Observable, Collection<E> {
    /**
     * Adds a Change Listener to this observable collection
     *
     * @param listener The listener to add
     */
    default void addListener(CollectionChangeListener<E> listener) {
        getHandler().addListener(listener);
    }
    
    /**
     * Removes a Change listener from this obserable collection
     *
     * @param listener The listener instance to remove
     */
    default void removeListener(CollectionChangeListener<E> listener) {
        getHandler().addListener(listener);
    }
    
    /**
     * Gets the handler for the change listeners
     *
     * @return The handler
     */
    CollectionListenerHandler<E> getHandler();
    
    /**
     * {@inheritDoc}
     */
    @Override
    Stream<E> stream();
    
    /**
     * {@inheritDoc}
     */
    @Override
    Stream<E> parallelStream();
}