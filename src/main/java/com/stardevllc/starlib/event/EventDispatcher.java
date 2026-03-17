package com.stardevllc.starlib.event;

import org.jetbrains.annotations.NotNull;

/**
 * This is meant to be a common interface for dispatching events to different systems.
 */
@FunctionalInterface
public interface EventDispatcher {
    EventDispatcher NOOP = new EventDispatcher() {
        @Override
        public <E> E dispatch(E event) {
            return event;
        }
    };
    
    <E> @NotNull E dispatch(E event);
    
    default void addListener(Object listener) {
        
    }
}