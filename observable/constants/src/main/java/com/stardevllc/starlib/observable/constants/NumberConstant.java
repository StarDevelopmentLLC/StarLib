package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.math.Operations;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.value.ObservableDoubleValue;
import com.stardevllc.starlib.observable.value.ObservableFloatValue;
import com.stardevllc.starlib.observable.value.ObservableLongValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;

import java.util.Objects;

public abstract class NumberConstant implements ObservableNumberValue {
    
    public static NumberConstant of(Number number) {
        if (number == null) {
            return IntegerConstant.valueOf(0);
        }
        
        if (number instanceof Long) {
            return LongConstant.valueOf((Long) number);
        } else if (number instanceof Double) {
            return DoubleConstant.valueOf((Double) number);
        } else if (number instanceof Float) {
            return FloatConstant.valueOf((Float) number);
        } else {
            return IntegerConstant.valueOf(number.intValue());
        }
    }

    @Override
    public ObservableValue<Number> add(Number other) {
        return add(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Number> subtract(Number other) {
        return subtract(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Number> multiply(Number other) {
        return multiply(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Number> divide(Number other) {
        return divide(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(Number other) {
        return isEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(Number other) {
        return isNotEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> greaterThan(Number other) {
        return greaterThan(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> lessThan(Number other) {
        return lessThan(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> greaterThanOrEqualTo(Number other) {
        return greaterThanOrEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Boolean> lessThanOrEqualTo(Number other) {
        return lessThanOrEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableValue<Number> orElse(Number constant) {
        return null;
    }

    @Override
    public ObservableValue<Boolean> isNull() {
        return BooleanConstant.FALSE; //Stored as a primitive
    }

    @Override
    public ObservableValue<Boolean> isNotNull() {
        return BooleanConstant.TRUE;
    }

    @Override
    public ObservableValue<Number> negate() {
        return of(Operations.negate(getValue()));
    }

    @Override
    public ObservableValue<Number> add(ObservableValue<Number> other) {
        return of(Operations.add(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> subtract(ObservableValue<Number> other) {
        return of(Operations.subtract(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> multiply(ObservableValue<Number> other) {
        return of(Operations.multiply(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> divide(ObservableValue<Number> other) {
        return of(Operations.divide(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<String> asString() {
        return StringConstant.valueOf(String.valueOf(getValue()));
    }

    @Override
    public ObservableValue<Boolean> greaterThan(ObservableValue<Number> other) {
        if (getValue() == null || other.getValue() == null) {
            return BooleanConstant.FALSE;
        }
        
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return BooleanConstant.valueOf(doubleValue() > other.getValue().doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return BooleanConstant.valueOf(floatValue() > other.getValue().floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return BooleanConstant.valueOf(longValue() > other.getValue().longValue());
        } else {
            return BooleanConstant.valueOf(intValue() > other.getValue().intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> lessThan(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() < other.getValue().doubleValue());
    }

    @Override
    public ObservableValue<Boolean> greaterThanOrEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() >= other.getValue().doubleValue());
    }

    @Override
    public ObservableValue<Boolean> lessThanOrEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() <= other.getValue().doubleValue());
    }
}
