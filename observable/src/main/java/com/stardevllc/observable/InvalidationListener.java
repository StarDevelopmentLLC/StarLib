package com.stardevllc.observable;

import com.stardevllc.eventbus.Event;
import com.stardevllc.eventbus.SubscribeEvent;

@SubscribeEvent
@FunctionalInterface
public interface InvalidationListener {
    void invalidated(InvalidationEvent event);
    record InvalidationEvent(Observable observable) implements Event {}
}
