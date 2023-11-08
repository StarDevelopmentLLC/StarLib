package com.stardevllc.starlib.observable;

import com.stardevllc.starlib.observable.property.BindChangeListener;

/**
 * An observable value that allows binding the value to another and/or vica versa <br>
 * You can find some default properties in the property sub-package
 * @param <T>
 */
public class Property<T> extends ObservableValue<T> {
    public Property() {}

    public Property(T object) {
        super(object);
    }

    /**
     * This type of bind is such that this value changes when the other value changes. But not the other way around.
     * @param observableValue The other value to bind to
     */
    public void bind(ObservableValue<T> observableValue) {
        observableValue.addChangeListener((value, oldValue, newValue) -> setValue(newValue));
    }

    /**
     * This allows for both values to change if each other change, keeping them in sync.
     * @param observableValue The other value
     */
    public void bidirectionalBind(ObservableValue<T> observableValue) {
        observableValue.addChangeListener((BindChangeListener<T>) (observable, oldObject, newObject) -> {
            value = newObject;
            for (ChangeListener<T> changeListener : this.changeListeners) {
                if (changeListener instanceof BindChangeListener<T>) {
                    continue;
                }
                
                changeListener.onChange(observable, oldObject, newObject);
            }
        });
        this.addChangeListener((BindChangeListener<T>) (observable, oldObject, newObject) -> {
            observableValue.value = newObject;
            for (ChangeListener<T> changeListener : observableValue.changeListeners) {
                if (changeListener instanceof BindChangeListener<T>) {
                    continue;
                }

                changeListener.onChange(observable, oldObject, newObject);
            }
        });
    }
}