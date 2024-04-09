/*
 * Copyright (c) 2010, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.stardevllc.starlib.observable.expression;

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.binding.*;
import com.stardevllc.starlib.observable.constants.DoubleConstant;
import com.stardevllc.starlib.observable.constants.FloatConstant;
import com.stardevllc.starlib.observable.constants.IntegerConstant;
import com.stardevllc.starlib.observable.constants.LongConstant;
import com.stardevllc.starlib.observable.value.*;

public abstract class NumberExpressionBase implements NumberExpression {
    protected ExpressionHelper<Number> helper;
    
    public NumberExpressionBase() {
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    public NumberBinding add(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> doubleValue() + other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> floatValue() + other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new LongBinding(() -> longValue() + other.longValue(), this, other);
        } else {
            return new IntegerBinding(() -> intValue() + other.intValue(), this, other);
        }
    }

    public NumberBinding subtract(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> doubleValue() - other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> floatValue() - other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new LongBinding(() -> longValue() - other.longValue(), this, other);
        } else {
            return new IntegerBinding(() -> intValue() - other.intValue(), this, other);
        }
    }

    public NumberBinding multiply(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> doubleValue() * other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> floatValue() * other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new LongBinding(() -> longValue() * other.longValue(), this, other);
        } else {
            return new IntegerBinding(() -> intValue() * other.intValue(), this, other);
        }
    }

    public NumberBinding divide(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> doubleValue() / other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> floatValue() / other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new LongBinding(() -> longValue() / other.longValue(), this, other);
        } else {
            return new IntegerBinding(() -> intValue() / other.intValue(), this, other);
        }
    }

    public BooleanBinding greaterThan(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> doubleValue() > other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> floatValue() > other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> longValue() > other.longValue(), this, other);
        } else {
            return new BooleanBinding(() -> intValue() > other.intValue(), this, other);
        }
    }

    public BooleanBinding lessThan(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> doubleValue() < other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> floatValue() < other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> longValue() < other.longValue(), this, other);
        } else {
            return new BooleanBinding(() -> intValue() < other.intValue(), this, other);
        }
    }

    public BooleanBinding greaterThanOrEqualTo(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> doubleValue() >= other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> floatValue() >= other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> longValue() >= other.longValue(), this, other);
        } else {
            return new BooleanBinding(() -> intValue() >= other.intValue(), this, other);
        }
    }

    public BooleanBinding lessThanOrEqualTo(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> doubleValue() <= other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> floatValue() <= other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> longValue() <= other.longValue(), this, other);
        } else {
            return new BooleanBinding(() -> intValue() <= other.intValue(), this, other);
        }
    }

    @Override
    public NumberBinding add(double other) {
        return add(DoubleConstant.valueOf(other));
    }

    @Override
    public NumberBinding add(float other) {
        return add(FloatConstant.valueOf(other));
    }

    @Override
    public NumberBinding add(long other) {
        return add(LongConstant.valueOf(other));
    }

    @Override
    public NumberBinding add(int other) {
        return add(IntegerConstant.valueOf(other));
    }

    @Override
    public NumberBinding subtract(final double other) {
        return subtract(DoubleConstant.valueOf(other));
    }

    @Override
    public NumberBinding subtract(final float other) {
        return subtract(FloatConstant.valueOf(other));
    }

    @Override
    public NumberBinding subtract(final long other) {
        return subtract(LongConstant.valueOf(other));
    }

    @Override
    public NumberBinding subtract(final int other) {
        return subtract(IntegerConstant.valueOf(other));
    }

    @Override
    public NumberBinding multiply(final double other) {
        return multiply(DoubleConstant.valueOf(other));
    }

    @Override
    public NumberBinding multiply(final float other) {
        return multiply(FloatConstant.valueOf(other));
    }

    @Override
    public NumberBinding multiply(final long other) {
        return multiply(LongConstant.valueOf(other));
    }

    @Override
    public NumberBinding multiply(final int other) {
        return multiply(IntegerConstant.valueOf(other));
    }

    @Override
    public NumberBinding divide(final double other) {
        return divide(DoubleConstant.valueOf(other));
    }

    @Override
    public NumberBinding divide(final float other) {
        return divide(FloatConstant.valueOf(other));
    }

    @Override
    public NumberBinding divide(final long other) {
        return divide(LongConstant.valueOf(other));
    }

    @Override
    public NumberBinding divide(final int other) {
        return divide(IntegerConstant.valueOf(other));
    }

    protected BooleanBinding equal(ObservableNumberValue other) {
        if ((this instanceof ObservableDoubleValue) || (other instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> doubleValue() == other.doubleValue(), this, other);
        } else if ((this instanceof ObservableFloatValue) || (other instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> this.floatValue() == other.floatValue(), this, other);
        } else if ((this instanceof ObservableLongValue) || (other instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> this.longValue() == other.longValue(), this, other);
        } else {
            return new BooleanBinding(() -> this.intValue() == other.intValue(), this, other);
        }
    }
    
    @Override
    public BooleanBinding isEqualTo(final ObservableNumberValue other) {
        return equal(other);
    }

    @Override
    public BooleanBinding isEqualTo(double other) {
        return equal(DoubleConstant.valueOf(other));
    }

    @Override
    public BooleanBinding isEqualTo(float other) {
        return equal(FloatConstant.valueOf(other));
    }

    @Override
    public BooleanBinding isEqualTo(long other) {
        return equal(LongConstant.valueOf(other));
    }

    @Override
    public BooleanBinding isEqualTo(int other) {
        return equal(IntegerConstant.valueOf(other));
    }

    @Override
    public BooleanBinding isNotEqualTo(ObservableNumberValue other) {
        return isEqualTo(other).not();
    }

    @Override
    public BooleanBinding isNotEqualTo(double other) {
        return isEqualTo(other).not();
    }

    @Override
    public BooleanBinding isNotEqualTo(float other) {
        return isEqualTo(other).not();
    }

    @Override
    public BooleanBinding isNotEqualTo(long other) {
        return isEqualTo(other).not();
    }

    @Override
    public BooleanBinding isNotEqualTo(int other) {
        return isEqualTo(other).not();
    }

    @Override
    public BooleanBinding greaterThan(final double other) {
        return greaterThan(DoubleConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThan(final float other) {
        return lessThan(FloatConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThan(final long other) {
        return greaterThan(LongConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThan(final int other) {
        return greaterThan(IntegerConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThan(final double other) {
        return lessThan(DoubleConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThan(final float other) {
        return lessThan(FloatConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThan(final long other) {
        return lessThan(LongConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThan(final int other) {
        return lessThan(IntegerConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final double other) {
        return greaterThanOrEqualTo(DoubleConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final float other) {
        return greaterThanOrEqualTo(FloatConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final long other) {
        return greaterThanOrEqualTo(LongConstant.valueOf(other));
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final int other) {
        return greaterThanOrEqualTo(IntegerConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final double other) {
        return lessThanOrEqualTo(DoubleConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final float other) {
        return lessThanOrEqualTo(FloatConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final long other) {
        return lessThanOrEqualTo(LongConstant.valueOf(other));
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final int other) {
        return lessThanOrEqualTo(IntegerConstant.valueOf(other));
    }

    @Override
    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public StringBinding asString(String format) {
        return (StringBinding) StringFormatter.format(format, this);
    }
}