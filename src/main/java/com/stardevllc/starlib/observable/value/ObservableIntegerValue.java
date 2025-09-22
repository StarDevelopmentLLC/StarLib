package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents an integer that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableIntegerValue extends ObservableNumberValue<Integer> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    int get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Integer getValue() {
        return get();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue negate() {
        return () -> NumberHelper.negate(get()).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue add(Number other) {
        return () -> NumberHelper.add(get(), other).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue add(ObservableValue<Number> other) {
        return () -> NumberHelper.add(get(), other.getValue()).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue subtract(Number other) {
        return () -> NumberHelper.subtract(get(), other).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue subtract(ObservableValue<Number> other) {
        return () -> NumberHelper.subtract(get(), other.getValue()).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue multiply(Number other) {
        return () -> NumberHelper.multiply(get(), other).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue multiply(ObservableValue<Number> other) {
        return () -> NumberHelper.multiply(get(), other.getValue()).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue divide(Number other) {
        return () -> NumberHelper.divide(get(), other).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue divide(ObservableValue<Number> other) {
        return () -> NumberHelper.divide(get(), other.getValue()).intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return () -> get() == other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return () -> get() == other.getValue().intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return () -> get() != other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return () -> get() != other.getValue().intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return () -> get() > other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return () -> get() > other.getValue().intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return () -> get() < other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return () -> get() < other.getValue().intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return () -> get() >= other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() >= other.getValue().intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return () -> get() <= other.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() <= other.getValue().intValue();
    }
}