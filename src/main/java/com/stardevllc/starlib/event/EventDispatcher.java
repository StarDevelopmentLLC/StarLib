package com.stardevllc.starlib.event;

import org.jetbrains.annotations.NotNull;

/**
 * This is meant to be a common interface for dispatching events to different systems.
 *
 * @param <E> The event type
 */
@FunctionalInterface
public interface EventDispatcher<E> {
    EventDispatcher<?> NOOP = new EventDispatcher<>() {
        @Override
        public <I> I dispatch(I event) {
            return event;
        }
    };
    
    <I extends E> @NotNull I dispatch(I event);
    
    default void addListener(Object listener) {
        
    }
}