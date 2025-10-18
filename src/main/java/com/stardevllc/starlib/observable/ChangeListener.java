package com.stardevllc.starlib.observable;

/**
 * Represents a listener for changes in an observable value
 *
 * @param <T> The value type
 */
@FunctionalInterface
public interface ChangeListener<T> {
    /**
     * Called when a change occurs
     *
     * @param observableValue The value that the change occured on
     * @param oldValue The old value
     * @param newValue The new value
     */
    void changed(ObservableValue<T> observableValue, T oldValue, T newValue);
}
