package com.stardevllc.observable.collections;

import com.stardevllc.eventbus.IEventBus;
import com.stardevllc.observable.Observable;
import com.stardevllc.observable.collections.event.MapChangeEvent;

import java.util.Map;

public interface ObservableMap<K, V> extends Observable, Map<K, V> {
    IEventBus<MapChangeEvent> eventBus();
}