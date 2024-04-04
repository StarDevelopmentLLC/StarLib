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
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableStringValue;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.util.function.Function;

public abstract class StringExpression implements ObservableStringValue {
    protected ExpressionHelper<String> helper;

    public StringExpression() {
    }

    @Override
    public String getValue() {
        return get();
    }

    public final String getValueSafe() {
        final String value = get();
        return value == null ? "" : value;
    }

    public StringExpression concat(Object other) {
        return Bindings.concat(this, other);
    }

    public BooleanBinding isEqualTo(final ObservableStringValue other) {
        return Bindings.equal(this, other);
    }

    public BooleanBinding isEqualTo(final String other) {
        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableStringValue other) {
        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNotEqualTo(final String other) {
        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isEqualToIgnoreCase(final ObservableStringValue other) {
        return Bindings.equalIgnoreCase(this, other);
    }

    public BooleanBinding isEqualToIgnoreCase(final String other) {
        return Bindings.equalIgnoreCase(this, other);
    }

    public BooleanBinding isNotEqualToIgnoreCase(
            final ObservableStringValue other) {
        return Bindings.notEqualIgnoreCase(this, other);
    }

    public BooleanBinding isNotEqualToIgnoreCase(final String other) {
        return Bindings.notEqualIgnoreCase(this, other);
    }

    public BooleanBinding greaterThan(final ObservableStringValue other) {
        return Bindings.greaterThan(this, other);
    }

    public BooleanBinding greaterThan(final String other) {
        return Bindings.greaterThan(this, other);
    }

    public BooleanBinding lessThan(final ObservableStringValue other) {
        return Bindings.lessThan(this, other);
    }

    public BooleanBinding lessThan(final String other) {
        return Bindings.lessThan(this, other);
    }

    public BooleanBinding greaterThanOrEqualTo(final ObservableStringValue other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    public BooleanBinding greaterThanOrEqualTo(final String other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    public BooleanBinding lessThanOrEqualTo(final ObservableStringValue other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    public BooleanBinding lessThanOrEqualTo(final String other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }

    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    public IntegerBinding length() {
        return Bindings.length(this);
    }

    public BooleanBinding isEmpty() {
        return Bindings.isEmpty(this);
    }

    public BooleanBinding isNotEmpty() {
        return Bindings.isNotEmpty(this);
    }

    @Override
    public <U> ObservableValue<U> map(Function<? super String, ? extends U> function) {
        return new MappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<String> orElse(String string) {
        return new OrElseBinding<>(this, string);
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super String, ? extends ObservableValue<? extends U>> function) {
        return new FlatMappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<String> when(ObservableValue<Boolean> observableValue) {
        return new ConditionalBinding<>(this, observableValue);
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
    public void addListener(ChangeListener<? super String> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }
}