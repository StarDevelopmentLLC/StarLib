package com.stardevllc.starlib.observable.value;

/**
 * Represents a string that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableStringValue extends ObservableObjectValue<String> {
    /**
     * Gets the value as a safe string
     *
     * @return The safe value
     */
    default String getValueSafe() {
        return get() == null ? "" : get();
    }
}