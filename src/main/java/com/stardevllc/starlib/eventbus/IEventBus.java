package com.stardevllc.starlib.eventbus;

public interface IEventBus<T> {
    <E extends T> E post(E event);
    boolean subscribe(Object object);
    boolean unsubscribe(Object object);
    Class<T> getEventClass();
    void clearListeners();
    
    default void addChildBus(IEventBus<?> childBus) {
        
    }
}
