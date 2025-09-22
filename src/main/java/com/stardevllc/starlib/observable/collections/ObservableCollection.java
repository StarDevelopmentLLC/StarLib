package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;
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
    void addListener(CollectionChangeListener<E> listener);
    
    /**
     * Removes a Change listener from this obserable collection
     *
     * @param listener The listener instance to remove
     */
    void removeListener(CollectionChangeListener<E> listener);
    
    /**
     * The eventbus handles the change listeners
     *
     * @return The eventbus of the observable collection
     */
    IEventBus<CollectionChangeEvent<E>> eventBus();
    
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