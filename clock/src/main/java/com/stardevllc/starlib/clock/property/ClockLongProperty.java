package com.stardevllc.starlib.clock.property;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.property.writable.LongProperty;
import com.stardevllc.starlib.observable.property.writable.Property;

public class ClockLongProperty extends LongProperty {
    public ClockLongProperty(Clock<?> bean, String propertyName, long value) {
        super(bean, propertyName, value);
    }

    @Override
    public void bindBidirectional(Property<Number> other) {
        //no-op
    }

    @Override
    public void bind(ObservableValue<? extends Number> rawObservable) {
        //no-op
    }
}
