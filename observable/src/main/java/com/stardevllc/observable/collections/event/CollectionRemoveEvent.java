package com.stardevllc.observable.collections.event;

import com.stardevllc.observable.collections.ObservableCollection;

public record CollectionRemoveEvent(ObservableCollection<?> collection, Object removed) implements CollectionChangeEvent {
}
