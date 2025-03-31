package com.stardevllc.observable.constants;

import com.stardevllc.observable.value.ObservableUUIDValue;

import java.util.UUID;

public class UUIDConstant extends ObjectConstant<UUID> implements ObservableUUIDValue {
    public UUIDConstant(UUID object) {
        super(object);
    }
}
