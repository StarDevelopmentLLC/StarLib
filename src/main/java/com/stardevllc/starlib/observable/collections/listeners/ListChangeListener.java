package com.stardevllc.starlib.observable.collections.listeners;

import com.stardevllc.starlib.observable.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface ListChangeListener<T> {
    void onChange(Change<T> change);

    class Change<T> {
        protected final ObservableList<T> list;
        protected List<T> added = new ArrayList<>();
        protected List<T> removed = new ArrayList<>();

        public Change(ObservableList<T> list) {
            this.list = list;
        }

        public ObservableList<T> getList() {
            return list;
        }

        public List<T> getAdded() {
            return added;
        }

        public List<T> getRemoved() {
            return removed;
        }
    }
}