package com.stardevllc.property;

import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableLongValue;
import com.stardevllc.starlib.numbers.SimpleCalculator;

public class LongProperty extends NumberProperty<Long> implements WritableLongValue {
    
    protected long value;
    
    public LongProperty(Object bean, String name, long value) {
        super(bean, name);
        this.value = value;
    }
    
    public LongProperty(String name, long value) {
        this(null, name, value);
    }
    
    public LongProperty(long value) {
        this(null, "", value);
    }
    
    public LongProperty() {
        this(null, "", 0L);
    }

    @Override
    public Class<Long> getTypeClass() {
        return Long.class;
    }

    @Override
    public void set(long newValue) {
        long oldValue = value;
        value = newValue;
        if (oldValue != newValue) {
            this.eventBus.post(new ChangeEvent<>(this, oldValue, newValue));
        }
    }

    @Override
    public void setValue(Long newValue) {
        set(newValue);
    }

    @Override
    public long get() {
        return value;
    }

    @Override
    public ObservableNumberValue<Long> negate() {
        return new LongProperty((Long) SimpleCalculator.negate(value));
    }

    @Override
    public ObservableNumberValue<Long> add(Number number) {
        return new LongProperty((Long) SimpleCalculator.add(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> subtract(Number number) {
        return new LongProperty((Long) SimpleCalculator.subtract(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> multiply(Number number) {
        return new LongProperty((Long) SimpleCalculator.multiply(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> divide(Number number) {
        return new LongProperty((Long) SimpleCalculator.divide(value, number.longValue()));
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number number) {
        return new BooleanProperty(get() == number.longValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number number) {
        return new BooleanProperty(get() != number.longValue());
    }

    @Override
    public ObservableBooleanValue greaterThan(Number number) {
        return new BooleanProperty(get() > number.longValue());
    }

    @Override
    public ObservableBooleanValue lessThan(Number number) {
        return new BooleanProperty(get() < number.longValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number number) {
        return new BooleanProperty(get() >= number.longValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number number) {
        return new BooleanProperty(get() <= number.longValue());
    }

    @Override
    public Long getValue() {
        return value;
    }
}
