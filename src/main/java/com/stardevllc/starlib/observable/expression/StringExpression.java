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
import com.stardevllc.starlib.observable.binding.BooleanBinding;
import com.stardevllc.starlib.observable.binding.IntegerBinding;
import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.constants.StringConstant;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

import static com.stardevllc.starlib.helper.StringHelper.getStringSafe;

public abstract class StringExpression implements ObservableStringValue {
    protected ExpressionHelper<String> helper;

    public StringExpression() {
    }

    @Override
    public String getValue() {
        return get();
    }

    public final String getValueSafe() {
        return getStringSafe(get());
    }

    public StringExpression concat(Object other) {
        return StringFormatter.concat(this, other);
    }

    public BooleanBinding isEqualTo(ObservableStringValue other) {
        return new BooleanBinding(() -> getStringSafe(get()).equals(getStringSafe(get())), this, other);
    }

    public BooleanBinding isEqualTo(final String other) {
        return isEqualTo(StringConstant.valueOf(other));
    }

    public BooleanBinding isNotEqualTo(final ObservableStringValue other) {
        return new BooleanBinding(() -> !getStringSafe(get()).equals(getStringSafe(get())), this, other);
    }

    public BooleanBinding isNotEqualTo(final String other) {
        return isNotEqualTo(StringConstant.valueOf(other));
    }

    public BooleanBinding isEqualToIgnoreCase(final ObservableStringValue other) {
        return new BooleanBinding(() -> getStringSafe(get()).equalsIgnoreCase(getStringSafe(get())), this, other);
    }

    public BooleanBinding isEqualToIgnoreCase(final String other) {
        return isEqualToIgnoreCase(StringConstant.valueOf(other));
    }

    public BooleanBinding isNotEqualToIgnoreCase(final ObservableStringValue other) {
        return new BooleanBinding(() -> !getStringSafe(get()).equalsIgnoreCase(getStringSafe(get())), this, other);
    }

    public BooleanBinding isNotEqualToIgnoreCase(final String other) {
        return isNotEqualToIgnoreCase(StringConstant.valueOf(other));
    }

    public BooleanBinding isNull() {
        return new BooleanBinding(() -> get() == null, this);
    }

    public BooleanBinding isNotNull() {
        return new BooleanBinding(() -> get() != null, this);
    }

    public IntegerBinding length() {
        return new IntegerBinding(() -> getValueSafe().length(), this);
    }

    public BooleanBinding isEmpty() {
        return new BooleanBinding(() -> getValueSafe().isEmpty(), this);
    }

    public BooleanBinding isNotEmpty() {
        return new BooleanBinding(() -> !getValueSafe().isEmpty(), this);
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