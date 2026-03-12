package com.stardevllc.starlib.values.observable;

import com.stardevllc.starlib.values.ObservableValue;
import com.stardevllc.starlib.values.observable.listener.ChangeListener;
import com.stardevllc.starlib.values.observable.listener.InvalidationListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableValue<T> implements ObservableValue<T> {
    
    protected final List<ChangeListener<T>> changeListeners = new ArrayList<>();
    protected final List<InvalidationListener> invalidationListeners = new ArrayList<>();
    
    private boolean valid = true;
    
    @Override
    public void invalidate() {
        if (this.valid) {
            this.valid = false;
            fireInvalidationListeners();
        }
    }
    
    @Override
    public boolean isValid() {
        return valid;
    }
    
    protected final void checkValid() {
        if (!isValid()) {
            throw new IllegalStateException(getClass().getSimpleName() + " is invalid");
        }
    }
    
    protected final void fireInvalidationListeners() {
        invalidationListeners.forEach(l -> l.onInvalidate(this));
    }
    
    protected final void fireChangeListeners(T oldValue, T newValue) {
        changeListeners.forEach(l -> l.onChange(this, oldValue, newValue));
    }
    
    @Override
    public void addChangeListener(ChangeListener<T> listener) {
        this.changeListeners.add(listener);
    }
    
    @Override
    public void removeChangeListener(ChangeListener<T> listener) {
        this.changeListeners.remove(listener);
    }
    
    @Override
    public void addInvalidationListener(InvalidationListener listener) {
        this.invalidationListeners.add(listener);
    }
    
    @Override
    public void removeInvalidationListener(InvalidationListener listener) {
        this.invalidationListeners.remove(listener);
    }
}
