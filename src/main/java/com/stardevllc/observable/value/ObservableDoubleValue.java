package com.stardevllc.observable.value;

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.constants.BooleanConstant;
import com.stardevllc.observable.constants.DoubleConstant;

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
        return new DoubleConstant((double) NumberHelper.negate(get()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue add(Number other) {
        return new DoubleConstant((double) NumberHelper.add(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue subtract(Number other) {
        return new DoubleConstant((double) NumberHelper.subtract(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue multiply(Number other) {
        return new DoubleConstant((double) NumberHelper.multiply(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableDoubleValue divide(Number other) {
        return new DoubleConstant((double) NumberHelper.divide(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(get() == other.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(get() != other.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(get() > other.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(get() < other.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(get() >= other.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(get() <= other.doubleValue());
    }
}