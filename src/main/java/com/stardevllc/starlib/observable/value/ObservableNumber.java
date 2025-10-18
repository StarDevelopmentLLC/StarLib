package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.value.NumberValue;

/**
 * Represents a number that can be observed
 *
 * @param <T> The Number Type
 */
public interface ObservableNumber<T extends Number> extends ObservableValue<T>, NumberValue<T> {
}