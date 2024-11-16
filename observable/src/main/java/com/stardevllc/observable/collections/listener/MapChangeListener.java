package com.stardevllc.observable.collections.listener;

import com.stardevllc.eventbus.SubscribeEvent;
import com.stardevllc.observable.collections.event.MapChangeEvent;

@SubscribeEvent
@FunctionalInterface
public interface MapChangeListener {
    void changed(MapChangeEvent event);
}
