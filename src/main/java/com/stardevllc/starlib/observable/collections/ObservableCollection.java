package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.observable.Observable;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Represents an collection that can be observed for changes
 *
 * @param <E> The element type
 */
public interface ObservableCollection<E> extends Observable, Collection<E> {
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