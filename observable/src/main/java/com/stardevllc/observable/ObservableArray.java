package com.stardevllc.observable;

public interface ObservableArray<T> extends Observable {
    T get(int index);
    int length();
    
    void addListener(ArrayChangeListener<T> listener);
    void removeListener(ArrayChangeListener<T> listener);
}
