package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.value.WritableValue;

/**
 * Represents an ObserableValue that can be written to directly
 *
 * @param <T> The value tyupe
 */
public interface WritableObservableValue<T> extends ObservableValue<T>, WritableValue<T> {
}