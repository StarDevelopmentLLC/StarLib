package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;
/**
 * Represents a number that can be observed
 * 
 * @param <T> The Number Type
 */
public interface ObservableNumberValue<T extends Number> extends ObservableValue<T> {
    ObservableNumberValue<T> negate();

    ObservableNumberValue<T> add(Number other);

    default ObservableNumberValue<T> add(ObservableValue<Number> other) {
        return add(other.getValue());
    }

    ObservableNumberValue<T> subtract(Number other);

    default ObservableNumberValue<T> subtract(ObservableValue<Number> other) {
        return subtract(other.getValue());
    }

    ObservableNumberValue<T> multiply(Number other);

    default ObservableNumberValue<T> multiply(ObservableValue<Number> other) {
        return multiply(other.getValue());
    }

    ObservableNumberValue<T> divide(Number other);

    default ObservableNumberValue<T> divide(ObservableValue<Number> other) {
        return divide(other.getValue());
    }

    ObservableBooleanValue isEqualTo(Number other);

    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return isEqualTo(other.getValue());
    }

    ObservableBooleanValue isNotEqualTo(Number other);

    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return isNotEqualTo(other.getValue());
    }

    ObservableBooleanValue greaterThan(Number other);

    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return greaterThan(other.getValue());
    }

    ObservableBooleanValue lessThan(Number other);

    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return lessThan(other.getValue());
    }

    ObservableBooleanValue greaterThanOrEqualTo(Number other);

    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return greaterThanOrEqualTo(other.getValue());
    }

    ObservableBooleanValue lessThanOrEqualTo(Number other);

    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return lessThanOrEqualTo(other.getValue());
    }

    default int intValue() {
        return getValue().intValue();
    }

    default long longValue() {
        return getValue().longValue();
    }

    default float floatValue() {
        return getValue().floatValue();
    }

    default double doubleValue() {
        return getValue().doubleValue();
    }
}