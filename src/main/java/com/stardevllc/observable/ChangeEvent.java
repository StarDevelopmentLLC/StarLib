package com.stardevllc.observable;

/**
 * An Event that represents a change in an ObservableValue
 *
 * @param observableValue The ObservableValue that this changed applied in
 * @param oldValue        The old value
 * @param newValue        The new value
 * @param <T>             The value type
 */
public record ChangeEvent<T>(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
}
