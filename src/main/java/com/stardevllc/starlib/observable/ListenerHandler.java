package com.stardevllc.starlib.observable;

import java.util.*;

/**
 * Default handling for listeners that do not depend on the IEventBus
 *
 * @param <T> The value type
 */
@SuppressWarnings("RedundantNoArgConstructor")
public class ListenerHandler<T> {
    private List<ChangeListener<T>> listeners = new ArrayList<>();
    
    /**
     * Constructs a default listener handler
     */
    public ListenerHandler() {
    }
    
    /**
     * Adds a listener
     *
     * @param listener The listener to add
     */
    public void addListener(ChangeListener<T> listener) {
        listeners.add(listener);
    }
    
    /**
     * Removes a listener
     *
     * @param listener The listener
     */
    public void removeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
    }
    
    /**
     * Calls the listeners based on the changes. This checks for equality with {@code Objects.equals(oldValue, newValue}
     *
     * @param observable The observable that was the source of the change
     * @param oldValue   The old value
     * @param newValue   The new value
     */
    public void handleChange(ObservableValue<T> observable, T oldValue, T newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            for (ChangeListener<T> listener : listeners) {
                listener.changed(observable, oldValue, newValue);
            }
        }
    }
}