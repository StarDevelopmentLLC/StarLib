package com.stardevllc.starlib.values.observable.listener;

import com.stardevllc.starlib.event.bus.SubscribeEvent;
import com.stardevllc.starlib.values.ObservableValue;

@FunctionalInterface
@SubscribeEvent
public interface ChangeListener<T> {
    void onChange(ObservableValue<T> observableValue, T oldValue, T newValue);    
}