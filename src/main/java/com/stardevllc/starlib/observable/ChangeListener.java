package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.eventbus.SubscribeEvent;

/**
 * Represents a listener for changes in an observable value
 * @param <T> The value type
 */
@SubscribeEvent
@FunctionalInterface
public interface ChangeListener<T> {
    void changed(ChangeEvent<T> event);
}
