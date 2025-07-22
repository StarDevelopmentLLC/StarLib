package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableNumberValue;

/**
 * Represents a Writable Number Observable value
 * 
 * @param <T> The Number type
 */
public interface WritableNumberValue<T extends Number> extends ObservableNumberValue<T>, WritableValue<T> {
}