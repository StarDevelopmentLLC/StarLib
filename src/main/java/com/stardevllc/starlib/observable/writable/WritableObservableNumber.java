package com.stardevllc.starlib.observable.writable;

import com.stardevllc.starlib.observable.WritableObservableValue;
import com.stardevllc.starlib.observable.value.ObservableNumber;

/**
 * Represents a Writable Number Observable value
 * 
 * @param <T> The Number type
 */
public interface WritableObservableNumber<T extends Number> extends ObservableNumber<T>, WritableObservableValue<T> {
}