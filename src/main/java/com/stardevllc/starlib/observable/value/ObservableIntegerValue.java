package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.constants.BooleanConstant;
import com.stardevllc.starlib.observable.constants.IntegerConstant;

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
        return new IntegerConstant((int) NumberHelper.negate(get()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue add(Number other) {
        return new IntegerConstant((int) NumberHelper.add(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue subtract(Number other) {
        return new IntegerConstant((int) NumberHelper.subtract(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue multiply(Number other) {
        return new IntegerConstant((int) NumberHelper.multiply(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableIntegerValue divide(Number other) {
        return new IntegerConstant((int) NumberHelper.divide(get(), other));
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