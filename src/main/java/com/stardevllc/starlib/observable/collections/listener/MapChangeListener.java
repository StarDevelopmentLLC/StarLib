package com.stardevllc.starlib.observable.collections.listener;

import com.stardevllc.starlib.eventbus.SubscribeEvent;
import com.stardevllc.starlib.observable.collections.event.MapChangeEvent;

@SubscribeEvent
@FunctionalInterface
public interface MapChangeListener<K, V> {
    void changed(MapChangeEvent<K, V> event);
}
