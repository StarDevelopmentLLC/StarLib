package com.stardevllc.starlib.clock.property;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.property.writable.ReadWriteLongProperty;
import com.stardevllc.starlib.observable.ReadWriteProperty;

public class ClockLongProperty extends ReadWriteLongProperty {
    public ClockLongProperty(Clock<?> bean, String propertyName, long value) {
        super(bean, propertyName, value);
    }

    @Override
    public void bindBidirectional(ReadWriteProperty<Number> other) {
        //no-op
    }

    @Override
    public void bind(ObservableValue<? extends Number> rawObservable) {
        //no-op
    }
}
