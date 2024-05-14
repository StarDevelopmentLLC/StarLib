/*
 * Copyright (c) 2010, 2016, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.value;

import com.stardevllc.starlib.observable.constants.DoubleConstant;
import com.stardevllc.starlib.observable.constants.FloatConstant;
import com.stardevllc.starlib.observable.constants.IntegerConstant;
import com.stardevllc.starlib.observable.constants.LongConstant;

public interface ObservableNumberValue extends ObservableValue<Number> {
    ObservableValue<Number> negate();

    ObservableValue<Number> add(ObservableValue<Number> other);

    ObservableValue<Number> subtract(ObservableValue<Number> other);

    ObservableValue<Number> multiply(ObservableValue<Number> other);

    ObservableValue<Number> divide(ObservableValue<Number> other);

    ObservableValue<Boolean> isEqualTo(ObservableValue<Number> other);

    ObservableValue<Boolean> isNotEqualTo(ObservableValue<Number> other);

    ObservableValue<Boolean> greaterThan(ObservableValue<Number> other);

    ObservableValue<Boolean> lessThan(ObservableValue<Number> other);

    ObservableValue<Boolean> greaterThanOrEqualTo(ObservableValue<Number> other);

    ObservableValue<Boolean> lessThanOrEqualTo(ObservableValue<Number> other);

    default ObservableValue<Number> add(double other) {
        return add(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Number> add(float other) {
        return add(FloatConstant.valueOf(other));
    }

    default ObservableValue<Number> add(long other) {
        return add(LongConstant.valueOf(other));
    }

    default ObservableValue<Number> add(int other) {
        return add(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Number> subtract(double other) {
        return subtract(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Number> subtract(float other) {
        return subtract(FloatConstant.valueOf(other));
    }

    default ObservableValue<Number> subtract(long other) {
        return subtract(LongConstant.valueOf(other));
    }

    default ObservableValue<Number> subtract(int other) {
        return subtract(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Number> multiply(double other) {
        return multiply(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Number> multiply(float other) {
        return multiply(FloatConstant.valueOf(other));
    }

    default ObservableValue<Number> multiply(long other) {
        return multiply(LongConstant.valueOf(other));
    }

    default ObservableValue<Number> multiply(int other) {
        return multiply(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Number> divide(double other) {
        return divide(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Number> divide(float other) {
        return divide(FloatConstant.valueOf(other));
    }

    default ObservableValue<Number> divide(long other) {
        return divide(LongConstant.valueOf(other));
    }

    default ObservableValue<Number> divide(int other) {
        return divide(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isEqualTo(double other) {
        return isEqualTo(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isEqualTo(float other) {
        return isEqualTo(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isEqualTo(long other) {
        return isEqualTo(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isEqualTo(int other) {
        return isEqualTo(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isNotEqualTo(double other) {
        return isNotEqualTo(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isNotEqualTo(float other) {
        return isNotEqualTo(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isNotEqualTo(long other) {
        return isNotEqualTo(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> isNotEqualTo(int other) {
        return isNotEqualTo(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThan(double other) {
        return greaterThan(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThan(float other) {
        return greaterThan(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThan(long other) {
        return greaterThan(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThan(int other) {
        return greaterThan(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThan(double other) {
        return lessThan(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThan(float other) {
        return lessThan(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThan(long other) {
        return lessThan(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThan(int other) {
        return lessThan(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThanOrEqualTo(double other) {
        return greaterThanOrEqualTo(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThanOrEqualTo(float other) {
        return greaterThanOrEqualTo(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThanOrEqualTo(long other) {
        return greaterThanOrEqualTo(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> greaterThanOrEqualTo(int other) {
        return greaterThanOrEqualTo(IntegerConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThanOrEqualTo(double other) {
        return lessThanOrEqualTo(DoubleConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThanOrEqualTo(float other) {
        return lessThanOrEqualTo(FloatConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThanOrEqualTo(long other) {
        return lessThanOrEqualTo(LongConstant.valueOf(other));
    }

    default ObservableValue<Boolean> lessThanOrEqualTo(int other) {
        return lessThanOrEqualTo(IntegerConstant.valueOf(other));
    }

    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();
}