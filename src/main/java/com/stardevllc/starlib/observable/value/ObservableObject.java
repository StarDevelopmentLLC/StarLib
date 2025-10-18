package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.value.ObjectValue;

/**
 * Represents an Object that can be observed
 *
 * @param <T> The object type
 */
public interface ObservableObject<T> extends ObservableValue<T>, ObjectValue<T> {
}