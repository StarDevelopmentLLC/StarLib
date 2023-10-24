package com.stardevllc.starlib.observable.collections.listeners;

import com.stardevllc.starlib.observable.collections.ObservableSet;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface SetChangeListener<T> {
    void onChange(SetChangeListener.Change<T> change);

    class Change<T> {
        protected final ObservableSet<T> set;
        protected List<T> added = new ArrayList<>();
        protected List<T> removed = new ArrayList<>();

        public Change(ObservableSet<T> set) {
            this.set = set;
        }

        public ObservableSet<T> getSet() {
            return set;
        }

        public List<T> getAdded() {
            return added;
        }

        public List<T> getRemoved() {
            return removed;
        }
    }
}