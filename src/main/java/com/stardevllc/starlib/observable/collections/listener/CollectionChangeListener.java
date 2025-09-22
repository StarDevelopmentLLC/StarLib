package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.event.CollectionChangeEvent;

/**
 * Listener for collection change events
 *
 * @param <E> The element type
 */
@SubscribeEvent
@FunctionalInterface
public interface CollectionChangeListener<E> {
    /**
     * Called when changes occured
     *
     * @param event The event
     */
    void changed(CollectionChangeEvent<E> event);
}