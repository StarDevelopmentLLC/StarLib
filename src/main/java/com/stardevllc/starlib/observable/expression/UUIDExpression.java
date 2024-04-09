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
import com.stardevllc.starlib.observable.binding.*;
import com.stardevllc.starlib.observable.constants.UUIDConstant;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableUUIDValue;
import com.stardevllc.starlib.observable.value.ObservableValue;

import java.util.UUID;
import java.util.function.Function;

public abstract class UUIDExpression implements ObservableUUIDValue {
    protected ExpressionHelper<UUID> helper;
    
    public UUIDExpression() {
    }

    @Override
    public UUID getValue() {
        return get();
    }
    
    public BooleanBinding isEqualTo(final ObservableUUIDValue other) {
        return new BooleanBinding(() -> get().equals(other.get()), this, other);
    }
    
    public BooleanBinding isEqualTo(final UUID other) {
        return isEqualTo(UUIDConstant.valueOf(other));
    }
    
    public BooleanBinding isNotEqualTo(final ObservableUUIDValue other) {
        return isEqualTo(other).not();
    }
    
    public BooleanBinding isNotEqualTo(final UUID other) {
        return isNotEqualTo(UUIDConstant.valueOf(other));
    }
    
    public BooleanBinding isNull() {
        return new BooleanBinding(() -> get() == null, this, this);
    }
    
    public BooleanBinding isNotNull() {
        return isNull().not();
    }

    public ObjectExpression<UUID> asObject() {
        return new ObjectBinding<>(this::get, this);
    }

    @Override
    public <U> ObservableValue<U> map(Function<? super UUID, ? extends U> function) {
        return new MappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<UUID> orElse(UUID uuid) {
        return new OrElseBinding<>(this, uuid);
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super UUID, ? extends ObservableValue<? extends U>> function) {
        return new FlatMappedBinding<>(this, function);
    }

    @Override
    public ObservableValue<UUID> when(ObservableValue<Boolean> observableValue) {
        return new ConditionalBinding<>(this, observableValue);
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
    public void addListener(ChangeListener<? super UUID> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super UUID> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }
}