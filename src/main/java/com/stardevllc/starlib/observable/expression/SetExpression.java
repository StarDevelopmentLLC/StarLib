/*
 * Copyright (c) 2011, 2023, Oracle and/or its affiliates. All rights reserved.
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
import com.stardevllc.starlib.observable.binding.StringBinding;
import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.collections.set.ObservableSet;
import com.stardevllc.starlib.observable.collections.set.SetChangeListener;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableSetValue;

import java.util.Collection;
import java.util.Iterator;

public abstract class SetExpression<E> implements ObservableSetValue<E> {
    protected SetExpressionHelper<E> helper;

    public SetExpression() {
    }

    @Override
    public ObservableSet<E> getValue() {
        return get();
    }

    public int getSize() {
        return size();
    }

    public BooleanBinding isEqualTo(final ObservableSet<?> other) {
        return new BooleanBinding(() -> get().equals(other), this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableSet<?> other) {
        return new BooleanBinding(() -> !get().equals(other), this, other);
    }

    public BooleanBinding isNull() {
        return new BooleanBinding(() -> get() == null, this);
    }

    public BooleanBinding isNotNull() {
        return new BooleanBinding(() -> get() != null, this);
    }

    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public int size() {
        return getNonNull().size();
    }

    @Override
    public boolean isEmpty() {
        return getNonNull().isEmpty();
    }

    @Override
    public boolean contains(Object obj) {
        return getNonNull().contains(obj);
    }

    @Override
    public Iterator<E> iterator() {
        return getNonNull().iterator();
    }

    @Override
    public Object[] toArray() {
        return getNonNull().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return getNonNull().toArray(array);
     }

    @Override
    public boolean add(E element) {
        return getNonNull().add(element);
    }

    @Override
    public boolean remove(Object obj) {
        return getNonNull().remove(obj);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return getNonNull().containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return getNonNull().removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return getNonNull().retainAll(objects);
    }

    @Override
    public void clear() {
        getNonNull().clear();
    }

    private ObservableSet<E> getNonNull() {
        ObservableSet<E> set = get();

        return set == null ? StarCollections.emptyObservableSet() : set;
    }

    @Override
    public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        SetExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
        SetExpressionHelper.fireValueChangedEvent(helper, change);
    }
}