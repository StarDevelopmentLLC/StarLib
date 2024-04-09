/*
 * Copyright (c) 2010, 2022, Oracle and/or its affiliates. All rights reserved.
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
import com.stardevllc.starlib.observable.binding.StringBinding;
import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;

public abstract class BooleanExpression implements ObservableBooleanValue {
    protected ExpressionHelper<Boolean> helper;
    
    public BooleanExpression() {
    }

    @Override
    public Boolean getValue() {
        return get();
    }

    public BooleanBinding and(final ObservableBooleanValue other) {
        return new BooleanBinding(() -> get() && other.get(), this, other);
    }

    public BooleanBinding or(final ObservableBooleanValue other) {
        return new BooleanBinding(() -> get() || other.get(), this, other);
    }

    public BooleanBinding not() {
        return new BooleanBinding(() -> !get(), this);
    }

    public BooleanBinding isEqualTo(final ObservableBooleanValue other) {
        return new BooleanBinding(() -> get() == other.get(), this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableBooleanValue other) {
        return new BooleanBinding(() -> get() != other.get(), this, other);
    }

    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
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
    public void addListener(ChangeListener<? super Boolean> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }
}