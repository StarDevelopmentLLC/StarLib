package com.stardevllc.starlib.value;

/**
 * Represents a Writable Double Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableDoubleValue extends DoubleValue, WritableNumberValue<Double> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(double value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(double amount) {
        set(get() + amount);
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(DoubleValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default double getAndAdd(double amount) {
        double v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default double getAndAdd(DoubleValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default double addAndGet(double amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default double addAndGet(DoubleValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(double amount) {
        set(get() - amount);
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(DoubleValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default double getAndSubtract(double amount) {
        double v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default double getAndSubtract(DoubleValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default double subtractAndGet(double amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default double subtractAndGet(DoubleValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(double factor) {
        set(get() * factor);
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(DoubleValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default double getAndMultiply(double factor) {
        double v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default double getAndMultiply(DoubleValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default double multiplyAndGet(double factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default double multiplyAndGet(DoubleValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(double divisor) {
        set(get() / divisor);
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(DoubleValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default double getAndDivide(double divisor) {
        double v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default double getAndDivide(DoubleValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default double divideAndGet(double divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default double divideAndGet(DoubleValue divisor) {
        return divideAndGet(divisor.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Double value) {
        set(value);
    }
}