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

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.binding.BooleanBinding;
import com.stardevllc.starlib.observable.binding.ObjectBinding;
import com.stardevllc.starlib.observable.binding.StringBinding;
import com.stardevllc.starlib.observable.binding.StringFormatter;
import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.collections.list.ListChangeListener;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.value.ChangeListener;
import com.stardevllc.starlib.observable.value.ObservableIntegerValue;
import com.stardevllc.starlib.observable.value.ObservableListValue;

import java.util.*;

public abstract class ListExpression<E> implements ObservableListValue<E> {
    protected ListExpressionHelper<E> helper = null;
    
    public ListExpression() {
    }

    @Override
    public ObservableList<E> getValue() {
        return get();
    }

    public int getSize() {
        return size();
    }

    public ObjectBinding<E> valueAt(int index) {
        return new ObjectBinding<>(() -> get(index), this);
    }

    public ObjectBinding<E> valueAt(ObservableIntegerValue index) {
        return new ObjectBinding<>(() -> get(index.get()), this, index);
    }

    public BooleanBinding isEqualTo(final ObservableList<?> other) {
        return new BooleanBinding(() -> get().equals(other), this, other);
    }

    public BooleanBinding isNotEqualTo(final ObservableList<?> other) {
        return new BooleanBinding(() -> get() != null ? other != null : !get().equals(other), this, other);
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
        return new HashSet<>(getNonNull()).containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> elements) {
        return getNonNull().addAll(i, elements);
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

    @Override
    public E get(int i) {
        return getNonNull().get(i);
    }

    @Override
    public E set(int i, E element) {
        return getNonNull().set(i, element);
    }

    @Override
    public void add(int i, E element) {
        getNonNull().add(i, element);
    }

    @Override
    public E remove(int i) {
        return getNonNull().remove(i);
    }

    @Override
    public int indexOf(Object obj) {
        return getNonNull().indexOf(obj);
    }

    @Override
    public int lastIndexOf(Object obj) {
        return getNonNull().lastIndexOf(obj);
    }

    @Override
    public ListIterator<E> listIterator() {
        return getNonNull().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return getNonNull().listIterator(i);
    }

    @Override
    public List<E> subList(int from, int to) {
        return getNonNull().subList(from, to);
    }

    @Override
    public boolean addAll(E... elements) {
        return getNonNull().addAll(elements);
    }

    @Override
    public boolean setAll(E... elements) {
        return getNonNull().setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends E> elements) {
        return getNonNull().setAll(elements);
    }

    @Override
    public boolean removeAll(E... elements) {
        return getNonNull().removeAll(elements);
    }

    @Override
    public boolean retainAll(E... elements) {
        return getNonNull().retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        getNonNull().remove(from, to);
    }

    private ObservableList<E> getNonNull() {
        ObservableList<E> list = get();

        return list == null ? StarCollections.emptyObservableList() : list;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableList<E>> listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableList<E>> listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        helper = ListExpressionHelper.removeListener(helper, listener);
    }

    protected void fireValueChangedEvent() {
        ListExpressionHelper.fireValueChangedEvent(helper);
    }

    protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> change) {
        ListExpressionHelper.fireValueChangedEvent(helper, change);
    }
}