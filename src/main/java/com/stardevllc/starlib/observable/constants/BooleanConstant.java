package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;
import com.stardevllc.starlib.observable.value.ObservableValue;

public class BooleanConstant implements ObservableBooleanValue {
    
    public static final BooleanConstant TRUE = new BooleanConstant(true);
    public static final BooleanConstant FALSE = new BooleanConstant(false);
    
    private final boolean value;

    private BooleanConstant(boolean value) {
        this.value = value;
    }
    
    public static BooleanConstant valueOf(boolean value) {
        return new BooleanConstant(value);
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public ObservableBooleanValue and(ObservableBooleanValue other) {
        return new BooleanConstant(value && other.get());
    }

    @Override
    public ObservableBooleanValue or(ObservableBooleanValue other) {
        return new BooleanConstant(value || other.get());
    }

    @Override
    public ObservableBooleanValue not() {
        return new BooleanConstant(!value);
    }

    @Override
    public void addListener(ChangeListener<? super Boolean> listener) {
        //no-op
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        //no-op
    }

    @Override
    public Boolean getValue() {
        return get();
    }

    @Override
    public ObservableBooleanValue isNull() {
        return FALSE;
    }

    @Override
    public ObservableBooleanValue isNotNull() {
        return TRUE;
    }

    @Override
    public ObservableBooleanValue isEqualTo(ObservableValue<Boolean> other) {
        return new BooleanConstant(value == other.getValue());
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(ObservableValue<Boolean> other) {
        return new BooleanConstant(value != other.getValue());
    }

    @Override
    public ObservableStringValue asString() {
        return StringConstant.valueOf(String.valueOf(value));
    }

    @Override
    public ObservableStringValue asString(String format) {
        return StringFormatter.format(format, this);
    }
}
