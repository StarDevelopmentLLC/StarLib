package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.constants.BooleanConstant;
import com.stardevllc.starlib.observable.constants.NumberConstant;
import com.stardevllc.starlib.observable.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.value.*;

public abstract class ReadOnlyNumberProperty implements ReadOnlyProperty<Number>, ObservableNumberValue {
    protected ExpressionHelper<Number> helper;

    @Override
    public int intValue() {
        return getValue().intValue();
    }

    @Override
    public long longValue() {
        return getValue().longValue();
    }

    @Override
    public float floatValue() {
        return getValue().floatValue();
    }

    @Override
    public double doubleValue() {
        return getValue().doubleValue();
    }

    @Override
    public ObservableValue<String> asString() {
        return StringFormatter.convert(this);
    }

    @Override
    public ObservableValue<String> asString(String format) {
        return StringFormatter.format(format, this);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    @Override
    public ObservableValue<Boolean> isNull() {
        return new ReadOnlyBooleanProperty(getValue() == null);
    }

    @Override
    public ObservableValue<Boolean> isNotNull() {
        return new ReadOnlyBooleanProperty(getValue() != null);
    }

    @Override
    public ObservableNumberValue negate() {
        if (this instanceof ObservableDoubleValue) {
            return new ReadOnlyDoubleProperty(-doubleValue());
        } else if (this instanceof ObservableFloatValue) {
            return new ReadOnlyFloatProperty(-floatValue());
        } else if (this instanceof ObservableLongValue) {
            return new ReadOnlyLongProperty(longValue());
        } else {
            return new ReadOnlyIntegerProperty(intValue());
        }
    }

    @Override
    public ObservableValue<Number> add(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return NumberConstant.of(0);
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyDoubleProperty(doubleValue() + other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyFloatProperty(floatValue() + other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyLongProperty(longValue() + other.longValue());
        } else {
            return new ReadOnlyIntegerProperty(intValue() + other.intValue());
        }
    }

    @Override
    public ObservableValue<Number> subtract(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return NumberConstant.of(0);
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyDoubleProperty(doubleValue() - other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyFloatProperty(floatValue() - other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyLongProperty(longValue() - other.longValue());
        } else {
            return new ReadOnlyIntegerProperty(intValue() - other.intValue());
        }
    }

    @Override
    public ObservableValue<Number> multiply(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return NumberConstant.of(0);
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyDoubleProperty(doubleValue() * other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyFloatProperty(floatValue() * other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyLongProperty(longValue() * other.longValue());
        } else {
            return new ReadOnlyIntegerProperty(intValue() * other.intValue());
        }
    }

    @Override
    public ObservableValue<Number> divide(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return NumberConstant.of(0);
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyDoubleProperty(doubleValue() / other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyFloatProperty(floatValue() / other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyLongProperty(longValue() / other.longValue());
        } else {
            return new ReadOnlyIntegerProperty(intValue() / other.intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> greaterThan(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return BooleanConstant.FALSE;
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyBooleanProperty(doubleValue() > other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyBooleanProperty(floatValue() > other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyBooleanProperty(longValue() > other.longValue());
        } else {
            return new ReadOnlyBooleanProperty(intValue() > other.intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> lessThan(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return BooleanConstant.FALSE;
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyBooleanProperty(doubleValue() < other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyBooleanProperty(floatValue() < other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyBooleanProperty(longValue() < other.longValue());
        } else {
            return new ReadOnlyBooleanProperty(intValue() < other.intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> greaterThanOrEqualTo(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return BooleanConstant.FALSE;
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyBooleanProperty(doubleValue() >= other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyBooleanProperty(floatValue() >= other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyBooleanProperty(longValue() >= other.longValue());
        } else {
            return new ReadOnlyBooleanProperty(intValue() >= other.intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> lessThanOrEqualTo(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return BooleanConstant.FALSE;
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyBooleanProperty(doubleValue() <= other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyBooleanProperty(floatValue() <= other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyBooleanProperty(longValue() <= other.longValue());
        } else {
            return new ReadOnlyBooleanProperty(intValue() <= other.intValue());
        }
    }

    @Override
    public ObservableBooleanValue isEqualTo(ObservableValue<Number> otherValue) {
        if (getValue() == null || otherValue.getValue() == null) {
            return BooleanConstant.FALSE;
        }

        Number other = otherValue.getValue();

        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new ReadOnlyBooleanProperty(doubleValue() == other.doubleValue());
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new ReadOnlyBooleanProperty(this.floatValue() == other.floatValue());
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new ReadOnlyBooleanProperty(this.longValue() == other.longValue());
        } else {
            return new ReadOnlyBooleanProperty(this.intValue() == other.intValue());
        }
    }

    @Override
    public ObservableValue<Boolean> isNotEqualTo(ObservableValue<Number> other) {
        return isEqualTo(other).not();
    }
}
