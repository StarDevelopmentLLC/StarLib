package com.stardevllc.observable.writable;

import com.stardevllc.observable.WritableValue;
import com.stardevllc.observable.value.ObservableNumberValue;

public interface WritableNumberValue<T extends Number> extends ObservableNumberValue<T>, WritableValue<T> {
}