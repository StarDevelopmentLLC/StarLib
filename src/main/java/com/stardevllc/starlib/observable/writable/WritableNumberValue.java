package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableValue;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;

/**
 * Represents a Writable Number Observable value
 * 
 * @param <T> The Number type
 */
public interface WritableNumberValue<T extends Number> extends ObservableNumberValue<T>, WritableValue<T> {
}