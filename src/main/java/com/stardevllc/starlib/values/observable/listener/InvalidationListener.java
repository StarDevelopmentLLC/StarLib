package com.stardevllc.starlib.values.observable.listener;

import com.stardevllc.starlib.event.bus.SubscribeEvent;
import com.stardevllc.starlib.values.Observable;

@FunctionalInterface
@SubscribeEvent
public interface InvalidationListener {
    void onInvalidate(Observable observable);
}