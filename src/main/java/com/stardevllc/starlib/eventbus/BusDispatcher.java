package com.stardevllc.starlib.eventbus;

import com.stardevllc.starlib.event.EventDispatcher;

public final class BusDispatcher<E> implements EventDispatcher<E> {
    
    private final IEventBus<E> bus;
    
    public BusDispatcher(IEventBus<E> bus) {
        this.bus = bus;
    }
    
    @Override
    public <I extends E> I dispatch(I event) {
        return bus.post(event);
    }
    
    @Override
    public void addListener(Object listener) {
        bus.subscribe(listener);
    }
}
