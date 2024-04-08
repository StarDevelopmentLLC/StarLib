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

import com.stardevllc.starlib.observable.binding.Bindings;
import com.stardevllc.starlib.observable.binding.DoubleBinding;
import com.stardevllc.starlib.observable.binding.FloatBinding;
import com.stardevllc.starlib.observable.binding.ObjectBinding;
import com.stardevllc.starlib.observable.value.ObservableFloatValue;

public abstract class FloatExpression extends NumberExpressionBase implements ObservableFloatValue {
    public FloatExpression() {
    }

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return (long) get();
    }

    @Override
    public float floatValue() {
        return get();
    }

    @Override
    public double doubleValue() {
        return get();
    }

    @Override
    public Float getValue() {
        return get();
    }

    @Override
    public FloatBinding negate() {
        return new FloatBinding(() -> -this.get());
    }

    @Override
    public DoubleBinding add(final double other) {
        return Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(final float other) {
        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(final long other) {
        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(final int other) {
        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding subtract(final double other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(final float other) {
        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(final long other) {
        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(final int other) {
        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding multiply(final double other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(final float other) {
        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(final long other) {
        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(final int other) {
        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding divide(final double other) {
        return Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(final float other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(final long other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(final int other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    public ObjectExpression<Float> asObject() {
        return new ObjectBinding<>(this::get, this);
    }
}