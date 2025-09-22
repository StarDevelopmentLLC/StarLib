package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents a number that can be observed
 *
 * @param <T> The Number Type
 */
public interface ObservableNumberValue<T extends Number> extends ObservableValue<T> {
    
    /**
     * Negates the value
     *
     * @return The new value
     */
    ObservableNumberValue<T> negate();
    
    /**
     * Adds the value to the provided
     *
     * @param other The number to add
     * @return The new value
     */
    ObservableNumberValue<T> add(Number other);
    
    /**
     * Adds the value to the provided
     *
     * @param other The number to add
     * @return The new value
     */
    ObservableNumberValue<T> add(ObservableValue<Number> other);
    
    /**
     * Subtracts the other from this one
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> subtract(Number other);
    
    /**
     * Subtracts the other from this one
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> subtract(ObservableValue<Number> other);
    
    /**
     * Multiplies this number and the other together
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> multiply(Number other);
    
    /**
     * Multiplies this number and the other together
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> multiply(ObservableValue<Number> other);
    
    /**
     * Divides this number by the othe other
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> divide(Number other);
    
    /**
     * Divides this number by the othe other
     *
     * @param other The other
     * @return The new value
     */
    ObservableNumberValue<T> divide(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue isEqualTo(Number other);
    
    /**
     * Checks to see if this is equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue isEqualTo(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is not equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue isNotEqualTo(Number other);
    
    /**
     * Checks to see if this is not equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is greather than the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue greaterThan(Number other);
    
    /**
     * Checks to see if this is greather than the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue greaterThan(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is less than the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue lessThan(Number other);
    
    /**
     * Checks to see if this is less than the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue lessThan(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is greater than or equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue greaterThanOrEqualTo(Number other);
    
    /**
     * Checks to see if this is greater than or equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other);
    
    /**
     * Checks to see if this is less than or equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue lessThanOrEqualTo(Number other);
    
    /**
     * Checks to see if this is less than or equal to the other
     *
     * @param other The other
     * @return The new value
     */
    ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other);
    
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