package com.stardevllc.observable;

import com.stardevllc.eventbus.SubscribeEvent;

/**
 * Represents a listener for changes in an observable value
 * @param <T> The value type
 */
@SubscribeEvent
@FunctionalInterface
public interface ChangeListener<T> {
    void changed(ChangeEvent<T> event);
}
