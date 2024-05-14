package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.constants.BooleanConstant;
import com.stardevllc.starlib.observable.constants.NumberConstant;
import com.stardevllc.starlib.observable.value.*;

public abstract class ReadOnlyNumberProperty implements ReadOnlyProperty<Number>, ObservableNumberValue {
    protected final Object bean;
    protected final String name;

    public ReadOnlyNumberProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ObservableValue<Number> orElse(Number constant) {
        return this;
    }

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
        return new ReadOnlyStringProperty(getValue().toString());
    }

    @Override
    public ObservableValue<String> asString(String format) {
        return null;
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
}
