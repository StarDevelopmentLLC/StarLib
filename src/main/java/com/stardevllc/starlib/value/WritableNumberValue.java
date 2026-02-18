package com.stardevllc.starlib.value;

/**
 * Represents a Writable Number Observable value
 * 
 * @param <T> The Number type
 */
@Deprecated(since = "0.24.0")
public interface WritableNumberValue<T extends Number> extends NumberValue<T>, WritableValue<T> {
}