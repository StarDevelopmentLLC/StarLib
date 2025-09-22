package com.stardevllc.starlib.eventbus;

import java.lang.annotation.*;

/**
 * Marker annotation for a {@link com.stardevllc.starlib.eventbus.impl.SimpleEventBus}. Not required if using a custom event bus
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SubscribeEvent {
    /**
     * The priority of the event
     *
     * @return The priority. Default is NORMAL
     */
    EventPriority priority() default EventPriority.NORMAL;
    
    /**
     * Marks that this event ignores if it is cancelled by a previous handler
     *
     * @return The ignore status
     */
    boolean ignoreCancelled() default false;
}
