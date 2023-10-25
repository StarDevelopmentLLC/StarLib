package com.stardevllc.starlib.observable;

public class Property<T> extends ObservableValue<T> {
    public Property() {}

    public Property(T object) {
        super(object);
    }
    
    public void bind(ObservableValue<T> observableValue) {
        observableValue.addChangeListener((value, oldValue, newValue) -> setValue(newValue));
    }
    
    public void bidirectionalBind(ObservableValue<T> observableValue) {
        bind(observableValue);
        this.addChangeListener((value, oldValue, newValue) -> observableValue.setValue(newValue));
    }
}