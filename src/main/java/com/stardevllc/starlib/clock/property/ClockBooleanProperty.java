package com.stardevllc.starlib.clock.property;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.observable.property.BooleanProperty;

public class ClockBooleanProperty extends BooleanProperty {
    public ClockBooleanProperty(Clock<?> bean, String propertyName, boolean value) {
        super(bean, propertyName, value);
    }
}
