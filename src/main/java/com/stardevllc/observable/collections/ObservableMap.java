package com.stardevllc.observable.collections;

import com.stardevllc.eventbus.IEventBus;
import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.MapChangeEvent;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    /**
     * The event bus that controls listening for changes
     * @return The EventBus
     */
    IEventBus<MapChangeEvent> eventBus();
}