package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.numbers.Operations;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.value.*;

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
    public ObservableNumberValue add(Number other) {
        return add(NumberConstant.of(other));
    }

    @Override
    public ObservableNumberValue subtract(Number other) {
        return subtract(NumberConstant.of(other));
    }

    @Override
    public ObservableNumberValue multiply(Number other) {
        return multiply(NumberConstant.of(other));
    }

    @Override
    public ObservableNumberValue divide(Number other) {
        return divide(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue isEqualTo(Number other) {
        return isEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(Number other) {
        return isNotEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue greaterThan(Number other) {
        return greaterThan(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue lessThan(Number other) {
        return lessThan(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return greaterThanOrEqualTo(NumberConstant.of(other));
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(Number other) {
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
    public ObservableNumberValue negate() {
        return of(Operations.negate(getValue()));
    }

    @Override
    public ObservableNumberValue add(ObservableValue<Number> other) {
        return of(Operations.add(getValue(), other.getValue()));
    }

    @Override
    public ObservableNumberValue subtract(ObservableValue<Number> other) {
        return of(Operations.subtract(getValue(), other.getValue()));
    }

    @Override
    public ObservableNumberValue multiply(ObservableValue<Number> other) {
        return of(Operations.multiply(getValue(), other.getValue()));
    }

    @Override
    public ObservableNumberValue divide(ObservableValue<Number> other) {
        return of(Operations.divide(getValue(), other.getValue()));
    }

    @Override
    public ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(Objects.equals(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<String> asString() {
        return StringConstant.valueOf(String.valueOf(getValue()));
    }

    @Override
    public ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
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
    public ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() < other.getValue().doubleValue());
    }

    @Override
    public ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() >= other.getValue().doubleValue());
    }

    @Override
    public ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return BooleanConstant.valueOf(getValue().doubleValue() <= other.getValue().doubleValue());
    }
}
