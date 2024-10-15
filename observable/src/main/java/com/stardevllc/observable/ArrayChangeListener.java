package com.stardevllc.observable;

import com.stardevllc.eventbus.Event;
import com.stardevllc.eventbus.SubscribeEvent;

@SubscribeEvent
@FunctionalInterface
public interface ArrayChangeListener<T> {
    void changed(ArrayChangeEvent<T> event);
    record ArrayChangeEvent<T>(ObservableArray<T> array, int index, T oldValue, T newValue) implements Event {}
}
