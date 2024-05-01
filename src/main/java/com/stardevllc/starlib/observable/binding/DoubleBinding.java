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

import com.stardevllc.starlib.observable.collections.StarCollections;
import com.stardevllc.starlib.observable.expression.DoubleExpression;
import com.stardevllc.starlib.observable.expression.ExpressionHelper;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.list.ObservableList;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class DoubleBinding extends DoubleExpression implements NumberBinding {
    private Callable<Double> callable;
    private ObservableList<Observable> dependencies;

    private double value;
    private boolean valid;

    public DoubleBinding(Observable... dependencies) {
        if (dependencies == null) {
            this.dependencies = StarCollections.emptyObservableList();
        } else {
            this.dependencies = StarCollections.observableList(Arrays.asList(dependencies));
        }
        bind(dependencies);
    }

    public DoubleBinding(Callable<Double> callable, Observable... dependencies) {
        this(dependencies);
        this.callable = callable;
    }

    public DoubleBinding(double value, Observable... dependencies) {
        this(dependencies);
        this.value = value;
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
    public final double get() {
        if (!valid) {
            value = computeValue();
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
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }

    protected double computeValue() {
        if (!valid) {
            return 0.0;
        }

        if (callable != null){
            try {
                return callable.call();
            } catch (Exception e) {
                return 0.0;
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return valid ? "DoubleBinding [value: " + get() + "]"
                : "DoubleBinding [invalid]";
    }
}
