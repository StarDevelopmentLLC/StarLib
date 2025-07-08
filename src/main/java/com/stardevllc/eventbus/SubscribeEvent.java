package com.stardevllc.eventbus;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SubscribeEvent {
    EventPriority priority() default EventPriority.NORMAL;
    boolean ignoreCancelled() default false;
}
