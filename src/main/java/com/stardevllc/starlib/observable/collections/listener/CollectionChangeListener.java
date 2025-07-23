package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;

@SubscribeEvent
@FunctionalInterface
public interface CollectionChangeListener<E> {
    void changed(CollectionChangeEvent<E> event);
}