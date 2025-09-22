package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.ObservableValue;

/**
 * Represents a float that can be observed
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface ObservableFloatValue extends ObservableNumberValue<Float> {
    /**
     * Gets this value as a primitive
     *
     * @return The vlaue
     */
    float get();
    
    /**
     * {@inheritDoc}
     */
    @Override
    default Float getValue() {
        return get();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue negate() {
        return () -> NumberHelper.negate(get()).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue add(Number other) {
        return () -> NumberHelper.add(get(), other).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue add(ObservableValue<Number> other) {
        return () -> NumberHelper.add(get(), other.getValue()).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue subtract(Number other) {
        return () -> NumberHelper.subtract(get(), other).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue subtract(ObservableValue<Number> other) {
        return () -> NumberHelper.subtract(get(), other.getValue()).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue multiply(Number other) {
        return () -> NumberHelper.multiply(get(), other).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue multiply(ObservableValue<Number> other) {
        return () -> NumberHelper.multiply(get(), other.getValue()).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue divide(Number other) {
        return () -> NumberHelper.divide(get(), other).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue divide(ObservableValue<Number> other) {
        return () -> NumberHelper.divide(get(), other.getValue()).floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return () -> get() == other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return () -> get() == other.getValue().floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return () -> get() != other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return () -> get() != other.getValue().floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return () -> get() > other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return () -> get() > other.getValue().floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return () -> get() < other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return () -> get() < other.getValue().floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return () -> get() >= other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() >= other.getValue().floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return () -> get() <= other.floatValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return () -> get() <= other.getValue().floatValue();
    }
}