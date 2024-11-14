package com.stardevllc.observable.collections.listener;

import com.stardevllc.eventbus.SubscribeEvent;
import com.stardevllc.observable.collections.event.CollectionChangeEvent;

@SubscribeEvent
@FunctionalInterface
public interface CollectionChangeListener {
    void changed(CollectionChangeEvent event);
}