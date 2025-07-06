package com.stardevllc.eventbus;

public interface IEventBus<T> {
    <E extends T> E post(E event);
    void subscribe(Object object);
    void unsubscribe(Object object);
}
