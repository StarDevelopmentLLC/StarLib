package com.stardevllc.starlib.clock.property;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.observable.property.LongProperty;

public class ClockLongProperty extends LongProperty {
    public ClockLongProperty(Clock<?> bean, String propertyName, long value) {
        super(bean, propertyName, value);
    }
}
