package com.stardevllc.observable.collections;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;
import com.stardevllc.observable.collections.listener.CollectionChangeListener;

import java.util.Collection;
import java.util.stream.Stream;

public interface ObservableCollection<E> extends Observable, Collection<E> {
    void addListener(CollectionChangeListener listener);
    void removeListener(CollectionChangeListener listener);
    
    EventBus<CollectionChangeEvent<E>> eventBus();

    @Override
    Stream<E> stream();

    @Override
    Stream<E> parallelStream();
}