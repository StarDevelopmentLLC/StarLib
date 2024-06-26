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

import com.stardevllc.starlib.observable.ObservableValue;

public interface ObservableNumberValue extends ObservableValue<Number> {
    ObservableNumberValue negate();

    ObservableNumberValue add(ObservableValue<Number> other);

    ObservableNumberValue add(Number other);

    ObservableNumberValue subtract(ObservableValue<Number> other);

    ObservableNumberValue subtract(Number other);

    ObservableNumberValue multiply(ObservableValue<Number> other);

    ObservableNumberValue multiply(Number other);

    ObservableNumberValue divide(ObservableValue<Number> other);

    ObservableNumberValue divide(Number other);

    ObservableBooleanValue isEqualTo(ObservableValue<Number> other);

    ObservableBooleanValue isEqualTo(Number other);

    ObservableBooleanValue isNotEqualTo(ObservableValue<Number> other);

    ObservableBooleanValue isNotEqualTo(Number other);

    ObservableBooleanValue greaterThan(ObservableValue<Number> other);

    ObservableBooleanValue greaterThan(Number other);

    ObservableBooleanValue lessThan(ObservableValue<Number> other);

    ObservableBooleanValue lessThan(Number other);

    ObservableBooleanValue greaterThanOrEqualTo(ObservableValue<Number> other);

    ObservableBooleanValue greaterThanOrEqualTo(Number other);

    ObservableBooleanValue lessThanOrEqualTo(ObservableValue<Number> other);

    ObservableBooleanValue lessThanOrEqualTo(Number other);

    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();
}