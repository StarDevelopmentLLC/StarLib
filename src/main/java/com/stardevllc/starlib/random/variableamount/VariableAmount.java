/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.stardevllc.starlib.random.variableamount;

import com.stardevllc.starlib.temporal.Temporal;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a value which may vary randomly.
 */
public interface VariableAmount {

    /**
     * Creates a new 'fixed' variable amount, calls to {@link #getDouble} will
     * always return the fixed value.
     *
     * @param value The fixed value
     * @return A variable amount representation
     */
    static VariableAmount fixed(double value) {
        return new DoubleVariableAmount.Fixed(value);
    }
    
    /**
     * Creates a new 'fixed' variable amount, calls to {@link #getDouble} will
     * always return the fixed value.
     *
     * @param value The fixed value
     * @return A variable amount representation
     */
    static VariableAmount fixed(int value) {
        return new IntegerVariableAmount.Fixed(value);
    }
    
    /**
     * Creates a new 'fixed' variable amount, calls to {@link #getDouble} will
     * always return the fixed value.
     *
     * @param value The fixed value
     * @return A variable amount representation
     */
    static VariableAmount fixed(long value) {
        return new LongVariableAmount.Fixed(value);
    }
    
    static VariableAmount fixed(Temporal temporal) {
        return fixed(temporal.getMillis());
    }

    /**
     * Creates a new variable amount which return values between the given min
     * (inclusive) and max (exclusive).
     *
     * @param min The minimum of the range (inclusive)
     * @param max The maximum of the range (exclusive)
     * @return A variable amount representation
     */
    static VariableAmount range(double min, double max) {
        return new DoubleVariableAmount.Range(min, max);
    }
    
    static VariableAmount range(double min, double max, double divisor) {
        return new DoubleVariableAmount.Range(min, max, divisor);
    }
    
    static VariableAmount range(double min, double max, double divisor, int places) {
        return new DoubleVariableAmount.Range(min, max, divisor, places);
    }
    
    /**
     * Creates a new variable amount which return values between the given min
     * (inclusive) and max (exclusive).
     *
     * @param min The minimum of the range (inclusive)
     * @param max The maximum of the range (inclusive)
     * @return A variable amount representation
     */
    static VariableAmount range(int min, int max) {
        return new IntegerVariableAmount.Range(min, max);
    }
    
    static VariableAmount range(int min, int max, int divisor) {
        return new IntegerVariableAmount.Range(min, max, divisor);
    }
    
    /**
     * Creates a new variable amount which return values between the given min
     * (inclusive) and max (exclusive).
     *
     * @param min The minimum of the range (inclusive)
     * @param max The maximum of the range (inclusive)
     * @return A variable amount representation
     */
    static VariableAmount range(long min, long max) {
        return new LongVariableAmount.Range(min, max);
    }
    
    static VariableAmount range(long min, long max, long divisor) {
        return new LongVariableAmount.Range(min, max, divisor);
    }
    
    static VariableAmount range(Temporal min, Temporal max) {
        return range(min.getMillis(), max.getMillis());
    }
    
    static VariableAmount range(Temporal min, Temporal max, long divisor) {
        return range(min.getMillis(), max.getMillis(), divisor);
    }

    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(double base, double variance) {
        return new DoubleVariableAmount.BaseAndVariance(base, fixed(variance));
    }
    
    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(int base, int variance) {
        return new IntegerVariableAmount.BaseAndVariance(base, fixed(variance));
    }
    
    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(long base, long variance) {
        return new LongVariableAmount.BaseAndVariance(base, fixed(variance));
    }

    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(double base, VariableAmount variance) {
        return new DoubleVariableAmount.BaseAndVariance(base, variance);
    }
    
    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(int base, VariableAmount variance) {
        return new IntegerVariableAmount.BaseAndVariance(base, variance);
    }
    
    /**
     * Creates a new variable about which has a base and variance. The final
     * amount will be the base amount plus or minus a random amount between zero
     * (inclusive) and the variance (exclusive).
     *
     * @param base The base value
     * @param variance The variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithVariance(long base, VariableAmount variance) {
        return new LongVariableAmount.BaseAndVariance(base, variance);
    }

    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(double base, double addition) {
        return new DoubleVariableAmount.BaseAndAddition(base, fixed(addition));
    }
    
    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(int base, int addition) {
        return new IntegerVariableAmount.BaseAndAddition(base, fixed(addition));
    }
    
    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(long base, long addition) {
        return new LongVariableAmount.BaseAndAddition(base, fixed(addition));
    }

    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(double base, VariableAmount addition) {
        return new DoubleVariableAmount.BaseAndAddition(base, addition);
    }
    
    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(int base, VariableAmount addition) {
        return new IntegerVariableAmount.BaseAndAddition(base, addition);
    }
    
    /**
     * Creates a new variable amount which has a base and an additional amount.
     * The final amount will be the base amount plus a random amount between
     * zero (inclusive) and the additional amount (exclusive).
     *
     * @param base The base value
     * @param addition The additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithRandomAddition(long base, VariableAmount addition) {
        return new LongVariableAmount.BaseAndAddition(base, addition);
    }

    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(double base, double variance, double chance) {
        return new DoubleVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(int base, int variance, double chance) {
        return new IntegerVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(long base, long variance, double chance) {
        return new LongVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }

    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(double base, VariableAmount variance, double chance) {
        return new DoubleVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(int base, VariableAmount variance, double chance) {
        return new IntegerVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random variance. The chance should be between zero and one with a chance
     * of one signifying that the variance will always be applied. If the chance
     * succeeds then the final amount will be the base amount plus or minus a
     * random amount between zero (inclusive) and the variance (exclusive). If
     * the chance fails then the final amount will just be the base value.
     *
     * @param base The base value
     * @param variance The variance
     * @param chance The chance to apply the variance
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalVariance(long base, VariableAmount variance, double chance) {
        return new LongVariableAmount.OptionalAmount(base, chance, baseWithVariance(base, variance));
    }

    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(double base, double addition, double chance) {
        return new DoubleVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(int base, double addition, double chance) {
        return new IntegerVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(long base, double addition, double chance) {
        return new LongVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }

    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(double base, VariableAmount addition, double chance) {
        return new DoubleVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(int base, VariableAmount addition, double chance) {
        return new IntegerVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }
    
    /**
     * Creates a new variable about which has a base and a chance to apply a
     * random additional amount. The chance should be between zero and one with
     * a chance of one signifying that the additional amount will always be
     * applied. If the chance succeeds then the final amount will be the base
     * amount plus a random amount between zero (inclusive) and the additional
     * amount (exclusive). If the chance fails then the final amount will just
     * be the base value.
     *
     * @param base The base value
     * @param addition The additional amount
     * @param chance The chance to apply the additional amount
     * @return A variable amount representation
     */
    static VariableAmount baseWithOptionalAddition(long base, VariableAmount addition, double chance) {
        return new LongVariableAmount.OptionalAmount(base, chance, baseWithRandomAddition(base, addition));
    }

    /**
     * Gets an instance of the variable amount depending on the given random
     * object.
     *
     * @param random The random object
     * @return The amount
     */
    double getDouble(Random random);

    /**
     * Gets an instance of the variable amount using the thread's
     * {@link ThreadLocalRandom} instance.
     *
     * @return The amount
     */
    default double getDouble() {
        return getDouble(ThreadLocalRandom.current());
    }
    
    int getInt(Random random);
    
    default int getInt() {
        return getInt(ThreadLocalRandom.current());
    }
    
    long getLong(Random random);
    
    default long getLong() {
        return getLong(ThreadLocalRandom.current());
    }

    /**
     * Gets the amount as if from {@link #getDouble(Random)} but floored to the
     * nearest integer equivalent.
     *
     * @param random The random object
     * @return The floored amount
     */
    default int getFlooredAmount(Random random) {
        return (int) Math.floor(getDouble(random));
    }

    /**
     * Gets the amount as if from {@link #getDouble()} but floored to the
     * nearest integer equivalent.
     *
     * @return The floored amount
     */
    default int getFlooredAmount() {
        return (int) Math.floor(getDouble());
    }
    
    /**
     * Gets the amount as if from {@link #getDouble(Random)} but ceiled to the
     * nearest integer equivalent.
     *
     * @param random The random object
     * @return The ceiled amount
     */
    default int getCeilAmount(Random random) {
        return (int) Math.ceil(getDouble(random));
    }
    
    /**
     * Gets the amount as if from {@link #getDouble()} but ceiled to the
     * nearest integer equivalent.
     *
     * @return The ceiled amount
     */
    default int getCeilAmount() {
        return (int) Math.ceil(getDouble());
    }
}