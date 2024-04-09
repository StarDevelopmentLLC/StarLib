/*
 * Copyright (c) 2010, 2017, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.binding.BooleanBinding;
import com.stardevllc.starlib.observable.binding.NumberBinding;
import com.stardevllc.starlib.observable.binding.StringBinding;
import com.stardevllc.starlib.observable.value.ObservableNumberValue;

public interface NumberExpression extends ObservableNumberValue {

    NumberBinding negate();

    NumberBinding add(final ObservableNumberValue other);

    NumberBinding add(final double other);

    NumberBinding add(final float other);

    NumberBinding add(final long other);

    NumberBinding add(final int other);

    NumberBinding subtract(final ObservableNumberValue other);

    NumberBinding subtract(final double other);

    NumberBinding subtract(final float other);

    NumberBinding subtract(final long other);

    NumberBinding subtract(final int other);

    NumberBinding multiply(final ObservableNumberValue other);

    NumberBinding multiply(final double other);

    NumberBinding multiply(final float other);

    NumberBinding multiply(final long other);

    NumberBinding multiply(final int other);

    NumberBinding divide(final ObservableNumberValue other);

    NumberBinding divide(final double other);

    NumberBinding divide(final float other);

    NumberBinding divide(final long other);

    NumberBinding divide(final int other);

    BooleanBinding isEqualTo(final ObservableNumberValue other);

    BooleanBinding isEqualTo(final double other);

    BooleanBinding isEqualTo(final float other);

    BooleanBinding isEqualTo(final long other);

    BooleanBinding isEqualTo(final int other);

    BooleanBinding isNotEqualTo(final ObservableNumberValue other);

    BooleanBinding isNotEqualTo(final double other);

    BooleanBinding isNotEqualTo(final float other);

    BooleanBinding isNotEqualTo(final long other);

    BooleanBinding isNotEqualTo(final int other);

    BooleanBinding greaterThan(final ObservableNumberValue other);

    BooleanBinding greaterThan(final double other);

    BooleanBinding greaterThan(final float other);

    BooleanBinding greaterThan(final long other);

    BooleanBinding greaterThan(final int other);

    BooleanBinding lessThan(final ObservableNumberValue other);

    BooleanBinding lessThan(final double other);

    BooleanBinding lessThan(final float other);

    BooleanBinding lessThan(final long other);

    BooleanBinding lessThan(final int other);

    BooleanBinding greaterThanOrEqualTo(final ObservableNumberValue other);

    BooleanBinding greaterThanOrEqualTo(final double other);

    BooleanBinding greaterThanOrEqualTo(final float other);

    BooleanBinding greaterThanOrEqualTo(final long other);

    BooleanBinding greaterThanOrEqualTo(final int other);

    BooleanBinding lessThanOrEqualTo(final ObservableNumberValue other);

    BooleanBinding lessThanOrEqualTo(final double other);

    BooleanBinding lessThanOrEqualTo(final float other);

    BooleanBinding lessThanOrEqualTo(final long other);

    BooleanBinding lessThanOrEqualTo(final int other);

    StringBinding asString();

    StringBinding asString(String format);
}