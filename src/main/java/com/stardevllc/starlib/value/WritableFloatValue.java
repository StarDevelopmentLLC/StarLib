package com.stardevllc.starlib.value;

/**
 * Represents a Writable Float Observable value
 */
public interface WritableFloatValue extends FloatValue, WritableNumberValue<Float> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(float value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(float amount) {
        set(get() + amount);
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(FloatValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default float getAndAdd(float amount) {
        float v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default float getAndAdd(FloatValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default float addAndGet(float amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default float addAndGet(FloatValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(float amount) {
        set(get() - amount);
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(FloatValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default float getAndSubtract(float amount) {
        float v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default float getAndSubtract(FloatValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default float subtractAndGet(float amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default float subtractAndGet(FloatValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(float factor) {
        set(get() * factor);
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(FloatValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default float getAndMultiply(float factor) {
        float v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default float getAndMultiply(FloatValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default float multiplyAndGet(float factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default float multiplyAndGet(FloatValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(float divisor) {
        set(get() / divisor);
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(FloatValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default float getAndDivide(float divisor) {
        float v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default float getAndDivide(FloatValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default float divideAndGet(float divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default float divideAndGet(FloatValue divisor) {
        return divideAndGet(divisor.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Float value) {
        set(value);
    }
}