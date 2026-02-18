package com.stardevllc.starlib.value;

/**
 * Represents a number that can be observed
 *
 * @param <T> The Number Type
 */
@FunctionalInterface
@Deprecated(since = "0.24.0")
public interface NumberValue<T extends Number> extends Value<T> {
    /**
     * Returns the value as an int
     *
     * @return The int value equal to {@link Number#intValue()}
     */
    default int intValue() {
        return getValue().intValue();
    }
    
    /**
     * Returns the value as a long
     *
     * @return The int value equal to {@link Number#longValue()}
     */
    default long longValue() {
        return getValue().longValue();
    }
    
    /**
     * Returns the value as a float
     *
     * @return The int value equal to {@link Number#floatValue()}
     */
    default float floatValue() {
        return getValue().floatValue();
    }
    
    /**
     * Returns the value as a double
     *
     * @return The int value equal to {@link Number#doubleValue()}
     */
    default double doubleValue() {
        return getValue().doubleValue();
    }
}