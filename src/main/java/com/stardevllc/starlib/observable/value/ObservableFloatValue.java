package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.helper.NumberHelper;
import com.stardevllc.starlib.observable.constants.BooleanConstant;
import com.stardevllc.starlib.observable.constants.FloatConstant;

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
        return new FloatConstant((float) NumberHelper.negate(get()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue add(Number other) {
        return new FloatConstant((float) NumberHelper.add(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue subtract(Number other) {
        return new FloatConstant((float) NumberHelper.subtract(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue multiply(Number other) {
        return new FloatConstant((float) NumberHelper.multiply(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableFloatValue divide(Number other) {
        return new FloatConstant((float) NumberHelper.divide(get(), other));
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