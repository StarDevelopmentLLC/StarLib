package com.stardevllc.starlib.observable.collections.event;

import com.stardevllc.starlib.observable.collections.ObservableCollection;

public record CollectionChangeEvent<E>(ObservableCollection<E> collection, E added, E removed) {
}
