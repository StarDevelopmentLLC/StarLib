package com.stardevllc.observable.collections.event;

import com.stardevllc.observable.collections.ObservableCollection;

public record CollectionChangeEvent<E>(ObservableCollection<E> collection, E added, E removed) {
}
