package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents a double that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableDoubleValue extends ObservableNumberValue<Double> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    double get();
    
    /**
     * {@inheritDoc}
     */
    default Double getValue() {
        return get();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue negate() {
        return () -> NumberHelper.negate(get()).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue add(Number other) {
        return () -> NumberHelper.add(get(), other).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue add(ObservableValue<Number> other) {
        return () -> NumberHelper.add(get(), other.getValue()).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue subtract(Number other) {
        return () -> NumberHelper.subtract(get(), other).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue subtract(ObservableValue<Number> other) {
        return () -> NumberHelper.subtract(get(), other.getValue()).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue multiply(Number other) {
        return () -> NumberHelper.multiply(get(), other).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue multiply(ObservableValue<Number> other) {
        return () -> NumberHelper.multiply(get(), other.getValue()).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue divide(Number other) {
        return () -> NumberHelper.divide(get(), other).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue divide(ObservableValue<Number> other) {
        return () -> NumberHelper.divide(get(), other.getValue()).doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return () -> get() == other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return () -> get() == other.getValue().doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return () -> get() != other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return () -> get() != other.getValue().doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return () -> get() > other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return () -> get() > other.getValue().doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return () -> get() < other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return () -> get() < other.getValue().doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return () -> get() >= other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() >= other.getValue().doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return () -> get() <= other.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() <= other.getValue().doubleValue();
    }
}