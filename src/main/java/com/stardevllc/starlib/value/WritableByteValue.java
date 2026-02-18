package com.stardevllc.starlib.value;

/**
 * Represents a Writable Byte Observable value
 */
@Deprecated(since = "0.24.0")
public interface WritableByteValue extends ByteValue, WritableNumberValue<Byte> {
    /**
     * Sets the value to the provided value
     *
     * @param value The new value
     */
    void set(byte value);
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(byte amount) {
        set((byte) (get() + amount));
    }
    
    /**
     * Adds the amount to the current value
     *
     * @param amount The amount
     */
    default void add(ByteValue amount) {
        add(amount.get());
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default byte getAndAdd(byte amount) {
        byte v = get();
        add(amount);
        return v;
    }
    
    /**
     * Gets the current value, adds the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default byte getAndAdd(ByteValue amount) {
        return getAndAdd(amount.get());
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte addAndGet(byte amount) {
        add(amount);
        return get();
    }
    
    /**
     * Adds the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte addAndGet(ByteValue amount) {
        return addAndGet(amount.get());
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(byte amount) {
        set((byte) (get() - amount));
    }
    
    /**
     * Subtracts the amount from the current value
     *
     * @param amount The amount
     */
    default void subtract(ByteValue amount) {
        subtract(amount.get());
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default byte getAndSubtract(byte amount) {
        byte v = get();
        subtract(amount);
        return v;
    }
    
    /**
     * Gets the current value, subtracts the amount and then returns the previous value
     *
     * @param amount The amount
     * @return The previous value
     */
    default byte getAndSubtract(ByteValue amount) {
        return getAndSubtract(amount.get());
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte subtractAndGet(byte amount) {
        subtract(amount);
        return get();
    }
    
    /**
     * Subtracts the amount and then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte subtractAndGet(ByteValue amount) {
        return subtractAndGet(amount.get());
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(byte factor) {
        set((byte) (get() * factor));
    }
    
    /**
     * Multiplies the value by the factor
     *
     * @param factor The factor
     */
    default void multiply(ByteValue factor) {
        multiply(factor.get());
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default byte getAndMultiply(byte factor) {
        byte v = get();
        multiply(factor);
        return v;
    }
    
    /**
     * Gets the current value, multiplies and then returns the previous value
     *
     * @param factor The factor
     * @return The previous value
     */
    default byte getAndMultiply(ByteValue factor) {
        return getAndMultiply(factor.get());
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default byte multiplyAndGet(byte factor) {
        multiply(factor);
        return get();
    }
    
    /**
     * Multiplies and then returns the new value
     *
     * @param factor The factor
     * @return The new value
     */
    default byte multiplyAndGet(ByteValue factor) {
        return multiplyAndGet(factor.get());
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(byte divisor) {
        set((byte) (get() / divisor));
    }
    
    /**
     * Divides the value by the divisor
     *
     * @param divisor The divisor
     */
    default void divide(ByteValue divisor) {
        divide(divisor.get());
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default byte getAndDivide(byte divisor) {
        byte v = get();
        divide(divisor);
        return v;
    }
    
    /**
     * Gets the current value, divides and then returns the previous value
     *
     * @param divisor The divisor
     * @return The previous value
     */
    default byte getAndDivide(ByteValue divisor) {
        return getAndDivide(divisor.get());
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default byte divideAndGet(byte divisor) {
        divide(divisor);
        return get();
    }
    
    /**
     * Divides and then returns the new value
     *
     * @param divisor The divisor
     * @return The new value
     */
    default byte divideAndGet(ByteValue divisor) {
        return divideAndGet(divisor.get());
    }
    
    /**
     * Increments the current value by 1
     */
    default void increment() {
        increment((byte) 1);
    }
    
    /**
     * Gets the current value, increments by 1 and then returns the old value
     *
     * @return The old value
     */
    default byte getAndIncrement() {
        return getAndIncrement((byte) 1);
    }
    
    /**
     * Increments by 1 and then returns the new value
     *
     * @return The new value
     */
    default byte incrementAndGet() {
        return incrementAndGet((byte) 1);
    }
    
    /**
     * Decrements the current value by 1
     */
    default void decrement() {
        decrement((byte) 1);
    }
    
    /**
     * Gets the current value, decrements by 1 and then returns the old value
     *
     * @return The old value
     */
    default byte getAndDecrement() {
        return getAndDecrement((byte) 1);
    }
    
    /**
     * Decrements by 1, then returns the new value
     *
     * @return The new value
     */
    default byte decrementAndGet() {
        return decrementAndGet((byte) 1);
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(byte amount) {
        set((byte) (get() + amount));
    }
    
    /**
     * Increments by the amount
     *
     * @param amount The amount
     */
    default void increment(ByteValue amount) {
        increment(amount.get());
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default byte getAndIncrement(byte amount) {
        byte v = get();
        increment(amount);
        return v;
    }
    
    /**
     * Gets the current value, increments by amount then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default byte getAndIncrement(ByteValue amount) {
        return getAndIncrement(amount.get());
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte incrementAndGet(byte amount) {
        increment(amount);
        return get();
    }
    
    /**
     * Increments by the amount, then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte incrementAndGet(ByteValue amount) {
        return incrementAndGet(amount.get());
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(byte amount) {
        set((byte) (get() - amount));
    }
    
    /**
     * Decrements by the amount
     *
     * @param amount The amount
     */
    default void decrement(ByteValue amount) {
        decrement(amount.get());
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default byte getAndDecrement(byte amount) {
        byte v = get();
        decrement(amount);
        return v;
    }
    
    /**
     * Gets the current value, decrements by amount and then returns the old value
     *
     * @param amount The amount
     * @return The old value
     */
    default int getAndDecrement(ByteValue amount) {
        return getAndDecrement(amount.get());
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte decrementAndGet(byte amount) {
        decrement(amount);
        return get();
    }
    
    /**
     * Decrements by amount then returns the new value
     *
     * @param amount The amount
     * @return The new value
     */
    default byte decrementAndGet(ByteValue amount) {
        return decrementAndGet(amount.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default void setValue(Byte value) {
        set(value);
    }
}