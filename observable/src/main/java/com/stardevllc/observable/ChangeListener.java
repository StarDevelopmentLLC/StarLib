package com.stardevllc.observable;

import com.stardevllc.eventbus.Event;
import com.stardevllc.eventbus.SubscribeEvent;

@SubscribeEvent
@FunctionalInterface
public interface ChangeListener<T> {
    void changed(ChangeEvent<T> event);
    record ChangeEvent<T>(ObservableValue<? extends T> observableValue, T oldValue, T newValue) implements Event {}
}
