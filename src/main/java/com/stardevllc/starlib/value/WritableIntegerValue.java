package com.stardevllc.starlib.value;

/**
 * Represents a Writable Integer Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableIntegerValue extends IntegerValue, WritableNumberValue<Integer> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(int value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(int amount) {
        set(get() + amount);
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(IntegerValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default int getAndAdd(int amount) {
        int v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default int getAndAdd(IntegerValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int addAndGet(int amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int addAndGet(IntegerValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(int amount) {
        set(get() - amount);
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(IntegerValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default int getAndSubtract(int amount) {
        int v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default int getAndSubtract(IntegerValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int subtractAndGet(int amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int subtractAndGet(IntegerValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(int factor) {
        set(get() * factor);
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(IntegerValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default int getAndMultiply(int factor) {
        int v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default int getAndMultiply(IntegerValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default int multiplyAndGet(int factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default int multiplyAndGet(IntegerValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(int divisor) {
        set(get() / divisor);
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(IntegerValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default int getAndDivide(int divisor) {
        int v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default int getAndDivide(IntegerValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default int divideAndGet(int divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default int divideAndGet(IntegerValue divisor) {
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
    default int getAndIncrement() {
        return getAndIncrement(1);
    }
    
    /**
     * Increments by 1 and then returns the new value
     *
     * @return The new value
     */
    default int incrementAndGet() {
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
    default int getAndDecrement() {
        return getAndDecrement(1);
    }
    
    /**
     * Decrements by 1, then returns the new value
     *
     * @return The new value
     */
    default int decrementAndGet() {
        return decrementAndGet(1);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(int amount) {
        set(get() + amount);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(IntegerValue amount) {
        increment(amount.get());
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default int getAndIncrement(int amount) {
        int v = get();
        increment(amount);
        return v;
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default int getAndIncrement(IntegerValue amount) {
        return getAndIncrement(amount.get());
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int incrementAndGet(int amount) {
        increment(amount);
        return get();
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int incrementAndGet(IntegerValue amount) {
        return incrementAndGet(amount.get());
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(int amount) {
        set(get() - amount);
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(IntegerValue amount) {
        decrement(amount.get());
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default int getAndDecrement(int amount) {
        int v = get();
        decrement(amount);
        return v;
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default int getAndDecrement(IntegerValue amount) {
        return getAndDecrement(amount.get());
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int decrementAndGet(int amount) {
        decrement(amount);
        return get();
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default int decrementAndGet(IntegerValue amount) {
        return decrementAndGet(amount.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Integer value) {
        set(value);
    }
}