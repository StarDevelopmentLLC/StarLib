package com.stardevllc.observable;

import com.stardevllc.eventbus.SubscribeEvent;

@SubscribeEvent
@FunctionalInterface
public interface ChangeListener<T> {
    void changed(ChangeEvent<T> event);
}
