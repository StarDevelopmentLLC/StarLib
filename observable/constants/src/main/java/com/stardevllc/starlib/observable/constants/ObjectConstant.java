/*
 * Copyright (c) 2011, 2022, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.constants;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.value.ObservableBooleanValue;
import com.stardevllc.starlib.observable.value.ObservableObjectValue;
import com.stardevllc.starlib.observable.value.ObservableStringValue;

public class ObjectConstant<T> implements ObservableObjectValue<T> {

    protected final T value;

    protected ObjectConstant(T value) {
        this.value = value;
    }

    public static <T> ObjectConstant<T> valueOf(T value) {
        return new ObjectConstant<>(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public ObservableBooleanValue isNull() {
        return BooleanConstant.valueOf(value == null);
    }

    @Override
    public ObservableBooleanValue isNotNull() {
        return BooleanConstant.valueOf(value != null);
    }

    @Override
    public ObservableBooleanValue isEqualTo(ObservableValue<T> other) {
        if (value == null) {
            return BooleanConstant.FALSE;
        }
        
        return BooleanConstant.valueOf(value.equals(other.getValue()));
    }

    @Override
    public ObservableBooleanValue isNotEqualTo(ObservableValue<T> other) {
        if (value == null) {
            return BooleanConstant.FALSE;
        }
        
        return BooleanConstant.valueOf(!value.equals(other.getValue()));
    }

    @Override
    public ObservableStringValue asString() {
        if (value == null) {
            return StringConstant.valueOf("null");
        }
        return StringConstant.valueOf(value.toString());
    }

    @Override
    public ObservableValue<T> orElse(T constant) {
        return null;
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {

    }
}