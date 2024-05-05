package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.MathOperator;
import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.binding.StringFormatter;
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
    public void addListener(ChangeListener<? super Number> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {

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
        return of(NumberHelper.negate(getValue()));
    }

    @Override
    public ObservableValue<Number> add(ObservableValue<Number> other) {
        return of(MathOperator.ADD.calculate(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> subtract(ObservableValue<Number> other) {
        return of(MathOperator.SUBTRACT.calculate(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> multiply(ObservableValue<Number> other) {
        return of(MathOperator.MULTIPLY.calculate(getValue(), other.getValue()));
    }

    @Override
    public ObservableValue<Number> divide(ObservableValue<Number> other) {
        return of(MathOperator.DIVIDE.calculate(getValue(), other.getValue()));
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
    public ObservableValue<String> asString(String format) {
        return StringFormatter.format(format, this);
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
        return null;
    }

    @Override
    public ObservableValue<Boolean> greaterThanOrEqualTo(ObservableValue<Number> other) {
        return null;
    }

    @Override
    public ObservableValue<Boolean> lessThanOrEqualTo(ObservableValue<Number> other) {
        return null;
    }
}
