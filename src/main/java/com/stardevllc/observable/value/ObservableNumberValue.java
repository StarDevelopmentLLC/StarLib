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

package com.stardevllc.observable.value;

import com.stardevllc.observable.ObservableValue;

public interface ObservableNumberValue<T extends Number> extends ObservableValue<T> {
    ObservableNumberValue<T> negate();

    ObservableNumberValue<T> add(Number other);

    default ObservableNumberValue<T> add(ObservableValue<Number> other) {
        return add(other.getValue());
    }

    ObservableNumberValue<T> subtract(Number other);

    default ObservableNumberValue<T> subtract(ObservableValue<Number> other) {
        return subtract(other.getValue());
    }

    ObservableNumberValue<T> multiply(Number other);

    default ObservableNumberValue<T> multiply(ObservableValue<Number> other) {
        return multiply(other.getValue());
    }

    ObservableNumberValue<T> divide(Number other);

    default ObservableNumberValue<T> divide(ObservableValue<Number> other) {
        return divide(other.getValue());
    }

    ObservableBooleanValue isEqualTo(Number other);

    default ObservableBooleanValue isEqualTo(ObservableValue<Number> other) {
        return isEqualTo(other.getValue());
    }

    ObservableBooleanValue isNotEqualTo(Number other);

    default ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other) {
        return isNotEqualTo(other.getValue());
    }

    ObservableBooleanValue greaterThan(Number other);

    default ObservableBooleanValue greaterThan(ObservableValue<Number> other) {
        return greaterThan(other.getValue());
    }

    ObservableBooleanValue lessThan(Number other);

    default ObservableBooleanValue lessThan(ObservableValue<Number> other) {
        return lessThan(other.getValue());
    }

    ObservableBooleanValue greaterThanOrEqualTo(Number other);

    default ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other) {
        return greaterThanOrEqualTo(other.getValue());
    }

    ObservableBooleanValue lessThanOrEqualTo(Number other);

    default ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other) {
        return lessThanOrEqualTo(other.getValue());
    }

    default int intValue() {
        return getValue().intValue();
    }

    default long longValue() {
        return getValue().longValue();
    }

    default float floatValue() {
        return getValue().floatValue();
    }

    default double doubleValue() {
        return getValue().doubleValue();
    }
}