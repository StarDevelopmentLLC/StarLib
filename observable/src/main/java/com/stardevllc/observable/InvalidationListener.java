package com.stardevllc.observable;

@FunctionalInterface
public interface InvalidationListener {
    void invalidated(Observable observable);
}
