package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents a long that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableLongValue extends ObservableNumberValue<Long> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    long get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Long getValue() {
        return get();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue negate() {
        return () -> NumberHelper.negate(get()).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue add(Number other) {
        return () -> NumberHelper.add(get(), other).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue add(ObservableValue<Number> other) {
        return () -> NumberHelper.add(get(), other.getValue()).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue subtract(Number other) {
        return () -> NumberHelper.subtract(get(), other).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue subtract(ObservableValue<Number> other) {
        return () -> NumberHelper.subtract(get(), other.getValue()).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue multiply(Number other) {
        return () -> NumberHelper.multiply(get(), other).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue multiply(ObservableValue<Number> other) {
        return () -> NumberHelper.multiply(get(), other.getValue()).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue divide(Number other) {
        return () -> NumberHelper.divide(get(), other).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue divide(ObservableValue<Number> other) {
        return () -> NumberHelper.divide(get(), other.getValue()).longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return () -> get() == other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return () -> get() == other.getValue().longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return () -> get() != other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return () -> get() != other.getValue().longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return () -> get() > other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return () -> get() > other.getValue().longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return () -> get() < other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return () -> get() < other.getValue().longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return () -> get() >= other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() >= other.getValue().longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return () -> get() <= other.longValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() <= other.getValue().longValue();
    }
}