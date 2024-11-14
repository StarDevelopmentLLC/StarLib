package com.stardevllc.observable.collections.event;

import com.stardevllc.observable.collections.ObservableCollection;

public record CollectionAddEvent<E>(ObservableCollection<E> collection, E added, E removed) implements CollectionChangeEvent {
}
