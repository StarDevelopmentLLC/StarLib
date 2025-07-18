package com.stardevllc.observable.property;

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.ChangeEvent;
import com.stardevllc.observable.value.ObservableBooleanValue;
import com.stardevllc.observable.value.ObservableNumberValue;
import com.stardevllc.observable.writable.WritableLongValue;

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
        if (boundValue != null) {
            return;
        }
        
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
        if (boundValue != null) {
            return boundValue.getValue();
        }
        return value;
    }

    @Override
    public ObservableNumberValue<Long> negate() {
        return new LongProperty((Long) NumberHelper.negate(value));
    }

    @Override
    public ObservableNumberValue<Long> add(Number number) {
        return new LongProperty((Long) NumberHelper.add(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> subtract(Number number) {
        return new LongProperty((Long) NumberHelper.subtract(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> multiply(Number number) {
        return new LongProperty((Long) NumberHelper.multiply(value, number.longValue()));
    }

    @Override
    public ObservableNumberValue<Long> divide(Number number) {
        return new LongProperty((Long) NumberHelper.divide(value, number.longValue()));
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
        return get();
    }
}
