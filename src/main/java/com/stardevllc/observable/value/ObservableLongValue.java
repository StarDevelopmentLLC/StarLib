package com.stardevllc.observable.value;

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.constants.*;

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
        return new LongConstant((long) NumberHelper.negate(get()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue add(Number other) {
        return new LongConstant((long) NumberHelper.add(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue subtract(Number other) {
        return new LongConstant((long) NumberHelper.subtract(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue multiply(Number other) {
        return new LongConstant((long) NumberHelper.multiply(get(), other));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default ObservableLongValue divide(Number other) {
        return new LongConstant((long) NumberHelper.divide(get(), other));
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