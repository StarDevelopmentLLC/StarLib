package com.stardevllc.starlib.observable.collections;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    /**
     * The event bus that controls listening for changes
     * @return The EventBus
     */
    IEventBus<MapChangeEvent> eventBus();
}