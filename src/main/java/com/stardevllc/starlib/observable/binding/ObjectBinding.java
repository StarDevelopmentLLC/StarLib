/*
 * Copyright (c) 2010, 2023, Oracle and/or its affiliates. All rights reserved.
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

package com.stardevllc.starlib.observable.binding;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.expression.ObjectExpression;
import com.stardevllc.starlib.observable.value.ChangeListener;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class ObjectBinding<T> extends ObjectExpression<T> implements Binding<T> {
    private Callable<T> callable;
    private ObservableList<Observable> dependencies;

    private T value;
    private boolean valid = false;
    private boolean observed;

    public ObjectBinding(Observable... dependencies) {
        if (dependencies == null) {
            this.dependencies = StarCollections.emptyObservableList();
        } else {
            this.dependencies = StarCollections.observableList(Arrays.asList(dependencies));
        }
        bind(dependencies);
    }

    public ObjectBinding(Callable<T> callable, Observable... dependencies) {
        this(dependencies);
        this.callable = callable;
    }

    public ObjectBinding(T value, Observable... dependencies) {
        this(dependencies);
        this.value = value;
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        observed = observed || listener != null;
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
        observed = helper != null;
    }

    protected final void bind(Observable... dependencies) {
        
    }

    protected final void unbind(Observable... dependencies) {
        
    }

    @Override
    public void dispose() {
        if (!dependencies.isEmpty()) {
            unbind(dependencies);
        }
    }

    @Override
    public ObservableList<?> getDependencies() {
        return dependencies;
    }

    @Override
    public final T get() {
        if (!valid) {
            T computed = computeValue();

            if (!allowValidation()) {
                return computed;
            }

            value = computed;
            valid = true;
        }
        return value;
    }

    protected void onInvalidating() {
    }

    @Override
    public final void invalidate() {
        if (valid) {
            valid = false;
            onInvalidating();
            ExpressionHelper.fireValueChangedEvent(helper);

            /*
             * Cached value should be cleared to avoid a strong reference to stale data,
             * but only if this binding didn't become valid after firing the event:
             */

            if (!valid) {
                value = null;
            }
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }

    protected final boolean isObserved() {
        return observed;
    }

    protected boolean allowValidation() {
        return true;
    }

    protected T computeValue() {
        if (!valid) {
            return null;
        }

        if (callable != null){
            try {
                return callable.call();
            } catch (Exception e) {
                return null;
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return valid ? "ObjectBinding [value: " + get() + "]"
                : "ObjectBinding [invalid]";
    }
}