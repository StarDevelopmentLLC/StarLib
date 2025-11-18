package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.value.WritableBooleanValue;
import com.stardevllc.starlib.value.impl.SimpleBooleanValue;

/**
 * Represents a listener for changes in an observable value
 *
 * @param <T> The value type
 */
@FunctionalInterface
public interface ChangeListener<T> {
    
    record Change<T>(ObservableValue<T> observableValue, T oldValue, T newValue, WritableBooleanValue cancelled) {
        public Change(ObservableValue<T> observableValue, T oldValue, T newValue, WritableBooleanValue cancelled) {
            this.observableValue = observableValue;
            this.oldValue = oldValue;
            this.newValue = newValue;
            if (cancelled != null) {
                this.cancelled = cancelled;
            } else {
                this.cancelled = new SimpleBooleanValue();
            }
        }
        
        public Change(ObservableValue<T> observableValue, T oldValue, T newValue) {
            this(observableValue, oldValue, newValue, new SimpleBooleanValue());
        }
    }
    
    /**
     * Called when a change occurs
     *
     * @param change The change information
     */
    void changed(Change<T> change);
    
    /**
     * Called when a change occurs
     *
     * @param observableValue The value that the change occured on
     * @param oldValue        The old value
     * @param newValue        The new value
     */
    default void changed(ObservableValue<T> observableValue, T oldValue, T newValue) {
        changed(new Change<>(observableValue, oldValue, newValue));
    }
}
