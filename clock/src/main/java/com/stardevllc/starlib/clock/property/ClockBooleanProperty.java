package com.stardevllc.starlib.clock.property;

import com.stardevllc.starlib.clock.Clock;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.property.writable.ReadWriteBooleanProperty;
import com.stardevllc.starlib.observable.ReadWriteProperty;

public class ClockBooleanProperty extends ReadWriteBooleanProperty {
    public ClockBooleanProperty(Clock<?> bean, String propertyName, boolean value) {
        super(bean, propertyName, value);
    }

    @Override
    public void bind(ObservableValue<? extends Boolean> rawObservable) {
        //no-op
    }

    @Override
    public void bindBidirectional(ReadWriteProperty<Boolean> other) {
        //no-op
    }
}
