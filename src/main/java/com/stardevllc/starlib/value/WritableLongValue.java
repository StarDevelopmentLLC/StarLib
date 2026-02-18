package com.stardevllc.starlib.value;

/**
 * Represents a Writable Long Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableLongValue extends LongValue, WritableNumberValue<Long> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(long value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(long amount) {
        set(get() + amount);
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(LongValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default long getAndAdd(long amount) {
        long v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default long getAndAdd(LongValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long addAndGet(long amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long addAndGet(LongValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(long amount) {
        set(get() - amount);
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(LongValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default long getAndSubtract(long amount) {
        long v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default long getAndSubtract(LongValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long subtractAndGet(long amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long subtractAndGet(LongValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(long factor) {
        set(get() * factor);
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(LongValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default long getAndMultiply(long factor) {
        long v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default long getAndMultiply(LongValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default long multiplyAndGet(long factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default long multiplyAndGet(LongValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(long divisor) {
        set(get() / divisor);
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(LongValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default long getAndDivide(long divisor) {
        long v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default long getAndDivide(LongValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default long divideAndGet(long divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default long divideAndGet(LongValue divisor) {
        return divideAndGet(divisor.get());
    }
    
    /**
     * Increments the current value by 1
     */
    default void increment() {
        increment(1);
    }
    
    /**
     * Gets the current value, increments by 1 and then returns the old value
     *
     * @return The old value
     */
    default long getAndIncrement() {
        return getAndIncrement(1);
    }
    
    /**
     * Increments by 1 and then returns the new value
     *
     * @return The new value
     */
    default long incrementAndGet() {
        return incrementAndGet(1);
    }
    
    /**
     * Decrements the current value by 1
     */
    default void decrement() {
        decrement(1);
    }
    
    /**
     * Gets the current value, decrements by 1 and then returns the old value
     *
     * @return The old value
     */
    default long getAndDecrement() {
        return getAndDecrement(1);
    }
    
    /**
     * Decrements by 1, then returns the new value
     *
     * @return The new value
     */
    default long decrementAndGet() {
        return decrementAndGet(1);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(long amount) {
        set(get() + amount);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(LongValue amount) {
        increment(amount.get());
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default long getAndIncrement(long amount) {
        long v = get();
        increment(amount);
        return v;
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default long getAndIncrement(LongValue amount) {
        return getAndIncrement(amount.get());
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long incrementAndGet(long amount) {
        increment(amount);
        return get();
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long incrementAndGet(LongValue amount) {
        return incrementAndGet(amount.get());
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(long amount) {
        set(get() - amount);
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(LongValue amount) {
        decrement(amount.get());
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default long getAndDecrement(long amount) {
        long v = get();
        decrement(amount);
        return v;
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default long getAndDecrement(LongValue amount) {
        return getAndDecrement(amount.get());
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long decrementAndGet(long amount) {
        decrement(amount);
        return get();
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default long decrementAndGet(LongValue amount) {
        return decrementAndGet(amount.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Long value) {
        set(value);
    }
}