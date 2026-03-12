package com.stardevllc.starlib.collections.observable;

import com.stardevllc.starlib.values.Observable;

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