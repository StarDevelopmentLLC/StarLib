/*
 * Copyright (c) 2010, 2024, Oracle and/or its affiliates. All rights reserved.
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
import com.stardevllc.starlib.observable.value.ObservableObjectValue;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.util.Locale;
import java.util.function.Function;

public abstract class ObjectExpression<T> implements ObservableObjectValue<T> {
    protected ExpressionHelper<T> helper;

    @Override
    public T getValue() {
        return get();
    }

    public ObjectExpression() {
    }

    @Override
    public <U> ObservableValue<U> map(Function<? super T, ? extends U> function) {
        return new MappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<T> orElse(T t) {
        return new OrElseBinding<>(this, t);
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super T, ? extends ObservableValue<? extends U>> function) {
        return new FlatMappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<T> when(ObservableValue<Boolean> observableValue) {
        return new ConditionalBinding<>(this, observableValue);
    }

    public BooleanBinding isEqualTo(final ObservableObjectValue<?> other) {
        return Bindings.equal(this, other);
    }

    public BooleanBinding isEqualTo(final Object other) {
        return Bindings.equal(this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableObjectValue<?> other) {
        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNotEqualTo(final Object other) {
        return Bindings.notEqual(this, other);
    }

    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }

    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    public StringBinding asString(String format) {
        return (StringBinding) Bindings.format(format, this);
    }

    public StringBinding asString(Locale locale, String format) {
        return (StringBinding) Bindings.format(locale, format, this);
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
    public void addListener(ChangeListener<? super T> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }
}