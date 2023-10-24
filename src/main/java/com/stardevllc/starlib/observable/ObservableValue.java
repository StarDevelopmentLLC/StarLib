package com.stardevllc.starlib.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObservableValue<T> {
    protected T value;
    protected List<ChangeListener<T>> changeListeners = new ArrayList<>();
    
    public ObservableValue() {
    }
    
    public ObservableValue(T object) {
        this.value = object;
    }
    
    public T get() {
        return value;
    }
    
    public void set(T object) {
        if (!this.changeListeners.isEmpty()) {
            this.changeListeners.forEach(changeListener -> changeListener.onChange(this, this.value, object));
        }
        
        this.value = object;
    }
    
    public void addChangeListener(ChangeListener<T> changeListener) {
        this.changeListeners.add(changeListener);
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObservableValue<?> that = (ObservableValue<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}