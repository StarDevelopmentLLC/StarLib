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

package com.stardevllc.starlib.observable.binding;

import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.collections.list.ListChangeListener;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.expression.ListExpression;
import com.stardevllc.starlib.observable.expression.ListExpressionHelper;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class ListBinding<E> extends ListExpression<E> implements Binding<ObservableList<E>> {

    private Callable<ObservableList<E>> callable;
    private ObservableList<Observable> dependencies;
    private ObservableList<E> value;
    private boolean valid = false;

    private BindingHelperObserver observer;

    public ListBinding(Observable... dependencies) {
        if (dependencies == null) {
            this.dependencies = StarCollections.emptyObservableList();
        } else {
            this.dependencies = StarCollections.observableList(Arrays.asList(dependencies));
        }
        bind(dependencies);
    }

    public ListBinding(Callable<ObservableList<E>> callable, Observable... dependencies) {
        this(dependencies);
        this.callable = callable;
    }

    public ListBinding(ObservableList<E> value, Observable... dependencies) {
        this(dependencies);
        this.value = value;
    }

    private final ListChangeListener<E> listChangeListener = change -> {
        onInvalidating();
        ListExpressionHelper.fireValueChangedEvent(helper, change);
    };

    protected final void bind(Observable... dependencies) {
        if ((dependencies != null) && (dependencies.length > 0)) {
            if (observer == null) {
                observer = new BindingHelperObserver(this);
            }
            for (final Observable dep : dependencies) {
                if (dep != null) {
                    dep.addListener(observer);
                }
            }
        }
    }

    protected final void unbind(Observable... dependencies) {
        if (observer != null) {
            for (final Observable dep : dependencies) {
                if (dep != null) {
                    dep.removeListener(observer);
                }
            }
        }
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
    public final ObservableList<E> get() {
        if (!valid) {
            value = computeValue();
            valid = true;
            if (value != null) {
                value.addListener(listChangeListener);
            }
        }
        return value;
    }

    protected void onInvalidating() {
        //no-op
    }

    @Override
    public final void invalidate() {
        if (valid) {
            if (value != null) {
                value.removeListener(listChangeListener);
            }
            valid = false;
            onInvalidating();
            ListExpressionHelper.fireValueChangedEvent(helper);
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }

    protected ObservableList<E> computeValue() {
        if (!valid) {
            return StarCollections.emptyObservableList();
        }

        if (callable != null){
            try {
                return callable.call();
            } catch (Exception e) {
                return StarCollections.emptyObservableList();
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return valid ? "ListBinding [value: " + get() + "]"
                : "ListBinding [invalid]";
    }

}
