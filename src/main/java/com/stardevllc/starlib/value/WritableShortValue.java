package com.stardevllc.starlib.value;

/**
 * Represents a Writable Short Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableShortValue extends ShortValue, WritableNumberValue<Short> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(short value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(short amount) {
        set((short) (get() + amount));
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(ShortValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default short getAndAdd(short amount) {
        short v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default short getAndAdd(ShortValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short addAndGet(short amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short addAndGet(ShortValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(short amount) {
        set((short) (get() - amount));
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(ShortValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default short getAndSubtract(short amount) {
        short v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default short getAndSubtract(ShortValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short subtractAndGet(short amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short subtractAndGet(ShortValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(short factor) {
        set((short) (get() * factor));
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(ShortValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default short getAndMultiply(short factor) {
        short v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default short getAndMultiply(ShortValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default short multiplyAndGet(short factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default short multiplyAndGet(ShortValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(short divisor) {
        set((short) (get() / divisor));
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(ShortValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default short getAndDivide(short divisor) {
        short v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default short getAndDivide(ShortValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default short divideAndGet(short divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default short divideAndGet(ShortValue divisor) {
        return divideAndGet(divisor.get());
    }
    
    /**
     * Increments the current value by 1
     */
    default void increment() {
        increment((short) 1);
    }
    
    /**
     * Gets the current value, increments by 1 and then returns the old value
     *
     * @return The old value
     */
    default short getAndIncrement() {
        return getAndIncrement((short) 1);
    }
    
    /**
     * Increments by 1 and then returns the new value
     *
     * @return The new value
     */
    default short incrementAndGet() {
        return incrementAndGet((short) 1);
    }
    
    /**
     * Decrements the current value by 1
     */
    default void decrement() {
        decrement((short) 1);
    }
    
    /**
     * Gets the current value, decrements by 1 and then returns the old value
     *
     * @return The old value
     */
    default short getAndDecrement() {
        return getAndDecrement((short) 1);
    }
    
    /**
     * Decrements by 1, then returns the new value
     *
     * @return The new value
     */
    default short decrementAndGet() {
        return decrementAndGet((short) 1);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(short amount) {
        set((short) (get() + amount));
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(ShortValue amount) {
        increment(amount.get());
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default short getAndIncrement(short amount) {
        short v = get();
        increment(amount);
        return v;
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default short getAndIncrement(ShortValue amount) {
        return getAndIncrement(amount.get());
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short incrementAndGet(short amount) {
        increment(amount);
        return get();
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short incrementAndGet(ShortValue amount) {
        return incrementAndGet(amount.get());
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(short amount) {
        set((short) (get() - amount));
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(ShortValue amount) {
        decrement(amount.get());
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default short getAndDecrement(short amount) {
        short v = get();
        decrement(amount);
        return v;
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default short getAndDecrement(ShortValue amount) {
        return getAndDecrement(amount.get());
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short decrementAndGet(short amount) {
        decrement(amount);
        return get();
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default short decrementAndGet(ShortValue amount) {
        return decrementAndGet(amount.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Short value) {
        set(value);
    }
}