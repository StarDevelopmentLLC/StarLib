/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.helper.NumberHelper;
import com.stardevllc.observable.constants.*;
public interface ObservableIntegerValue extends ObservableNumberValue<Integer> {
    int get();
    @Override
    default Integer getValue() {
        return get();
    }
    @Override
    default ObservableIntegerValue negate() {
        return new IntegerConstant((int) NumberHelper.negate(get()));
    }
    @Override
    default ObservableIntegerValue add(Number other) {
        return new IntegerConstant((int) NumberHelper.add(get(), other));
    }
    @Override
    default ObservableIntegerValue subtract(Number other) {
        return new IntegerConstant((int) NumberHelper.subtract(get(), other));
    }
    @Override
    default ObservableIntegerValue multiply(Number other) {
        return new IntegerConstant((int) NumberHelper.multiply(get(), other));
    }
    @Override
    default ObservableIntegerValue divide(Number other) {
        return new IntegerConstant((int) NumberHelper.divide(get(), other));
    }
    @Override
    default ObservableBooleanValue isEqualTo(Number other) {
        return new BooleanConstant(get() == other.doubleValue());
    }
    @Override
    default ObservableBooleanValue isNotEqualTo(Number other) {
        return new BooleanConstant(get() != other.doubleValue());
    }
    @Override
    default ObservableBooleanValue greaterThan(Number other) {
        return new BooleanConstant(get() > other.doubleValue());
    }
    @Override
    default ObservableBooleanValue lessThan(Number other) {
        return new BooleanConstant(get() < other.doubleValue());
    }
    @Override
    default ObservableBooleanValue greaterThanOrEqualTo(Number other) {
        return new BooleanConstant(get() >= other.doubleValue());
    }
    @Override
    default ObservableBooleanValue lessThanOrEqualTo(Number other) {
        return new BooleanConstant(get() <= other.doubleValue());
    }
}