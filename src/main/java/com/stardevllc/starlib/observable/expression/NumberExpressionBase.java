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
import com.stardevllc.starlib.observable.value.*;

import java.util.Locale;
import java.util.function.Function;

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

    @Override
    public <U> ObservableValue<U> map(Function<? super Number, ? extends U> function) {
        return new MappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<Number> orElse(Number number) {
        return new OrElseBinding<>(this, number);
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super Number, ? extends ObservableValue<? extends U>> function) {
        return new FlatMappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<Number> when(ObservableValue<Boolean> observableValue) {
        return new ConditionalBinding<>(this, observableValue);
    }

    @Override
    public NumberBinding add(final ObservableNumberValue other) {
        return Bindings.add(this, other);
    }

    @Override
    public NumberBinding subtract(final ObservableNumberValue other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public NumberBinding multiply(final ObservableNumberValue other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public NumberBinding divide(final ObservableNumberValue other) {
        return Bindings.divide(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final ObservableNumberValue other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final ObservableNumberValue other,
                                    double epsilon) {
        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(final double other, double epsilon) {
        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(final float other, double epsilon) {
        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(final long other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final long other, double epsilon) {
        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isEqualTo(final int other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final int other, double epsilon) {
        return Bindings.equal(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(final ObservableNumberValue other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final ObservableNumberValue other,
                                       double epsilon) {
        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(final double other, double epsilon) {
        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(final float other, double epsilon) {
        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(final long other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final long other, double epsilon) {
        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding isNotEqualTo(final int other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final int other, double epsilon) {
        return Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public BooleanBinding greaterThan(final ObservableNumberValue other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final double other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final float other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final long other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final int other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final ObservableNumberValue other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final double other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final float other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final long other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final int other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final ObservableNumberValue other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final double other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final float other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final long other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final int other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final ObservableNumberValue other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final double other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final float other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final long other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final int other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public StringBinding asString(String format) {
        return (StringBinding) Bindings.format(format, this);
    }

    @Override
    public StringBinding asString(Locale locale, String format) {
        return (StringBinding) Bindings.format(locale, format, this);
    }
}