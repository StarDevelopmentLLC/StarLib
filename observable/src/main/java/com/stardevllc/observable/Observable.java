package com.stardevllc.observable;

public interface Observable {
    void addListener(InvalidationListener listener);
    void removeListener(InvalidationListener listener);
}