package com.stardevllc.starlib.observable;

/**
 * An observable value that allows binding the value to another and/or vica versa <br>
 * There are plans for default Properties for java types to make it easier, but that will be in future updates.
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
        bind(observableValue);
        this.addChangeListener((value, oldValue, newValue) -> observableValue.setValue(newValue));
    }
}