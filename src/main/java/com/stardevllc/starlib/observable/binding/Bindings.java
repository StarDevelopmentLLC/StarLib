/*
 * Copyright (c) 2010, 2022, Oracle and/or its affiliates. All rights reserved.
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

import com.stardevllc.starlib.observable.InvalidationListener;
import com.stardevllc.starlib.observable.Observable;
import com.stardevllc.starlib.observable.collections.list.ImmutableObservableList;
import com.stardevllc.starlib.observable.collections.list.ObservableList;
import com.stardevllc.starlib.observable.constants.*;
import com.stardevllc.starlib.observable.expression.StringExpression;
import com.stardevllc.starlib.observable.value.*;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.UUID;

public final class Bindings {

    private Bindings() {
    }

    // Sum

    private static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> op1.doubleValue() + op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> op1.floatValue() + op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> op1.longValue() + op2.longValue(), dependencies);
        } else {
            return new IntegerBinding(() -> op1.intValue() + op2.intValue(), dependencies);
        }
    }
    
    public static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return Bindings.add(op1, op2, op1, op2);
    }
    
    public static DoubleBinding add(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) Bindings.add(op1, DoubleConstant.valueOf(op2), op1);
    }
    
    public static DoubleBinding add(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) Bindings.add(DoubleConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding add(final ObservableNumberValue op1, float op2) {
        return Bindings.add(op1, FloatConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding add(float op1, final ObservableNumberValue op2) {
        return Bindings.add(FloatConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding add(final ObservableNumberValue op1, long op2) {
        return Bindings.add(op1, LongConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding add(long op1, final ObservableNumberValue op2) {
        return Bindings.add(LongConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding add(final ObservableNumberValue op1, int op2) {
        return Bindings.add(op1, IntegerConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding add(int op1, final ObservableNumberValue op2) {
        return Bindings.add(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Diff

    private static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> op1.doubleValue() - op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> op1.floatValue() - op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> op1.longValue() - op2.longValue(), dependencies);
        } else {
            return new IntegerBinding(() -> op1.intValue() - op2.intValue(), dependencies);
        }
    }
    
    public static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return Bindings.subtract(op1, op2, op1, op2);
    }
    
    public static DoubleBinding subtract(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) Bindings.subtract(op1, DoubleConstant.valueOf(op2), op1);
    }
    
    public static DoubleBinding subtract(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) Bindings.subtract(DoubleConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding subtract(final ObservableNumberValue op1, float op2) {
        return Bindings.subtract(op1, FloatConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding subtract(float op1, final ObservableNumberValue op2) {
        return Bindings.subtract(FloatConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding subtract(final ObservableNumberValue op1, long op2) {
        return Bindings.subtract(op1, LongConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding subtract(long op1, final ObservableNumberValue op2) {
        return Bindings.subtract(LongConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding subtract(final ObservableNumberValue op1, int op2) {
        return Bindings.subtract(op1, IntegerConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding subtract(int op1, final ObservableNumberValue op2) {
        return Bindings.subtract(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Multiply

    private static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> op1.doubleValue() * op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> op1.floatValue() * op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> op1.longValue() * op2.longValue(), dependencies);
        } else {
            return new IntegerBinding(() -> op1.intValue() * op2.intValue(), dependencies);
        }
    }
    
    public static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return Bindings.multiply(op1, op2, op1, op2);
    }
    
    public static DoubleBinding multiply(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) Bindings.multiply(op1, DoubleConstant.valueOf(op2), op1);
    }
    
    public static DoubleBinding multiply(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) Bindings.multiply(DoubleConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding multiply(final ObservableNumberValue op1, float op2) {
        return Bindings.multiply(op1, FloatConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding multiply(float op1, final ObservableNumberValue op2) {
        return Bindings.multiply(FloatConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding multiply(final ObservableNumberValue op1, long op2) {
        return Bindings.multiply(op1, LongConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding multiply(long op1, final ObservableNumberValue op2) {
        return Bindings.multiply(LongConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding multiply(final ObservableNumberValue op1, int op2) {
        return Bindings.multiply(op1, IntegerConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding multiply(int op1, final ObservableNumberValue op2) {
        return Bindings.multiply(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Divide

    private static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> op1.doubleValue() / op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> op1.floatValue() / op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> op1.longValue() / op2.longValue(), dependencies);
        } else {
            return new IntegerBinding(() -> op1.intValue() / op2.intValue(), dependencies);
        }
    }
    
    public static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return Bindings.divide(op1, op2, op1, op2);
    }
    
    public static DoubleBinding divide(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) Bindings.divide(op1, DoubleConstant.valueOf(op2), op1);
    }
    
    public static DoubleBinding divide(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) Bindings.divide(DoubleConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding divide(final ObservableNumberValue op1, float op2) {
        return Bindings.divide(op1, FloatConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding divide(float op1, final ObservableNumberValue op2) {
        return Bindings.divide(FloatConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding divide(final ObservableNumberValue op1, long op2) {
        return Bindings.divide(op1, LongConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding divide(long op1, final ObservableNumberValue op2) {
        return Bindings.divide(LongConstant.valueOf(op1), op2, op2);
    }
    
    public static NumberBinding divide(final ObservableNumberValue op1, int op2) {
        return Bindings.divide(op1, IntegerConstant.valueOf(op2), op1);
    }
    
    public static NumberBinding divide(int op1, final ObservableNumberValue op2) {
        return Bindings.divide(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Equals

    private static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> Math.abs(op1.doubleValue() + op2.doubleValue()) <= epsilon, dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> Math.abs(op1.floatValue() + op2.floatValue()) <= epsilon, dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> Math.abs(op1.longValue() + op2.longValue()) <= epsilon, dependencies);
        } else {
            return new BooleanBinding(() -> Math.abs(op1.intValue() + op2.intValue()) <= epsilon, dependencies);
        }
    }
    
    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon) {
        return Bindings.equal(op1, op2, epsilon, op1, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return equal(op1, op2, 0.0, op1, op2);
    }
    
    public static BooleanBinding equal(final ObservableNumberValue op1, final double op2, final double epsilon) {
        return equal(op1, DoubleConstant.valueOf(op2), epsilon,  op1);
    }
    
    public static BooleanBinding equal(final double op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final float op2, final double epsilon) {
        return equal(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final float op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2, final double epsilon) {
        return equal(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2) {
        return equal(op1, LongConstant.valueOf(op2), 0.0, op1);
    }

    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2) {
        return equal(LongConstant.valueOf(op1), op2, 0.0, op2);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2, final double epsilon) {
        return equal(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2) {
        return equal(op1, IntegerConstant.valueOf(op2), 0.0, op1);
    }

    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2) {
        return equal(IntegerConstant.valueOf(op1), op2, 0.0, op2);
    }

    // Not Equal

    private static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> Math.abs(op1.doubleValue() + op2.doubleValue()) > epsilon, dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> Math.abs(op1.floatValue() + op2.floatValue()) > epsilon, dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> Math.abs(op1.longValue() + op2.longValue()) > epsilon, dependencies);
        } else {
            return new BooleanBinding(() -> Math.abs(op1.intValue() + op2.intValue()) > epsilon, dependencies);
        }
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final double epsilon) {
        return Bindings.notEqual(op1, op2, epsilon, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return notEqual(op1, op2, 0.0, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final double op2, final double epsilon) {
        return notEqual(op1, DoubleConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final double op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final float op2, final double epsilon) {
        return notEqual(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final float op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2, final double epsilon) {
        return notEqual(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2) {
        return notEqual(op1, LongConstant.valueOf(op2), 0.0, op1);
    }

    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2) {
        return notEqual(LongConstant.valueOf(op1), op2, 0.0, op2);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2, final double epsilon) {
        return notEqual(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2) {
        return notEqual(op1, IntegerConstant.valueOf(op2), 0.0, op1);
    }

    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2) {
        return notEqual(IntegerConstant.valueOf(op1), op2, 0.0, op2);
    }

    // Greater Than

    private static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> op1.doubleValue() > op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> op1.floatValue() > op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> op1.longValue() > op2.longValue(), dependencies);
        } else {
            return new BooleanBinding(() -> op1.intValue() > op2.intValue(), dependencies);
        }
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return Bindings.greaterThan(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final double op2) {
        return greaterThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(final double op1, final ObservableNumberValue op2) {
        return greaterThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final float op2) {
        return greaterThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(final float op1, final ObservableNumberValue op2) {
        return greaterThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final long op2) {
        return greaterThan(op1, LongConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(final long op1, final ObservableNumberValue op2) {
        return greaterThan(LongConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final int op2) {
        return greaterThan(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(final int op1, final ObservableNumberValue op2) {
        return greaterThan(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Less Than

    private static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        return greaterThan(op2, op1, dependencies);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return lessThan(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final double op2) {
        return lessThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(final double op1, final ObservableNumberValue op2) {
        return lessThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final float op2) {
        return lessThan(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(final float op1, final ObservableNumberValue op2) {
        return lessThan(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final long op2) {
        return lessThan(op1, LongConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(final long op1, final ObservableNumberValue op2) {
        return lessThan(LongConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThan(final ObservableNumberValue op1, final int op2) {
        return lessThan(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(final int op1, final ObservableNumberValue op2) {
        return lessThan(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Greater Than or Equal

    private static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding(() -> op1.doubleValue() >= op2.doubleValue(), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding(() -> op1.floatValue() >= op2.floatValue(), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding(() -> op1.longValue() >= op2.longValue(), dependencies);
        } else {
            return new BooleanBinding(() -> op1.intValue() >= op2.intValue(), dependencies);
        }
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final double op2) {
        return greaterThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(final double op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final float op2) {
        return greaterThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(final float op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final long op2) {
        return greaterThanOrEqual(op1, LongConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(final long op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(LongConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final int op2) {
        return greaterThanOrEqual(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(final int op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Less Than or Equal

    private static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2, Observable... dependencies) {
        return greaterThanOrEqual(op2, op1, dependencies);
    }


    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final double op2) {
        return lessThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(final double op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final float op2) {
        return lessThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(final float op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final long op2) {
        return lessThanOrEqual(op1, LongConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(final long op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(LongConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final int op2) {
        return lessThanOrEqual(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(final int op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Minimum

    private static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> Math.min(op1.doubleValue(), op2.doubleValue()), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> Math.min(op1.floatValue(), op2.floatValue()), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> Math.min(op1.longValue(), op2.longValue()), dependencies);
        } else {
            return new IntegerBinding(() -> Math.min(op1.intValue(), op2.intValue()), dependencies);
        }
    }

    public static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return min(op1, op2, op1, op2);
    }

    public static DoubleBinding min(final ObservableNumberValue op1, final double op2) {
        return (DoubleBinding) min(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding min(final double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) min(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding min(final ObservableNumberValue op1, final float op2) {
        return min(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding min(final float op1, final ObservableNumberValue op2) {
        return min(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding min(final ObservableNumberValue op1, final long op2) {
        return min(op1, LongConstant.valueOf(op2), op1);
    }

    public static NumberBinding min(final long op1, final ObservableNumberValue op2) {
        return min(LongConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding min(final ObservableNumberValue op1, final int op2) {
        return min(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static NumberBinding min(final int op1, final ObservableNumberValue op2) {
        return min(IntegerConstant.valueOf(op1), op2, op2);
    }

    // Maximum

    private static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding(() -> Math.max(op1.doubleValue(), op2.doubleValue()), dependencies);
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding(() -> Math.max(op1.floatValue(), op2.floatValue()), dependencies);
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding(() -> Math.max(op1.longValue(), op2.longValue()), dependencies);
        } else {
            return new IntegerBinding(() -> Math.max(op1.intValue(), op2.intValue()), dependencies);
        }
    }

    public static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return max(op1, op2, op1, op2);
    }

    public static DoubleBinding max(final ObservableNumberValue op1, final double op2) {
        return (DoubleBinding) max(op1, DoubleConstant.valueOf(op2), op1);
    }

    public static DoubleBinding max(final double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) max(DoubleConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding max(final ObservableNumberValue op1, final float op2) {
        return max(op1, FloatConstant.valueOf(op2), op1);
    }

    public static NumberBinding max(final float op1, final ObservableNumberValue op2) {
        return max(FloatConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding max(final ObservableNumberValue op1, final long op2) {
        return max(op1, LongConstant.valueOf(op2), op1);
    }

    public static NumberBinding max(final long op1, final ObservableNumberValue op2) {
        return max(LongConstant.valueOf(op1), op2, op2);
    }

    public static NumberBinding max(final ObservableNumberValue op1, final int op2) {
        return max(op1, IntegerConstant.valueOf(op2), op1);
    }

    public static NumberBinding max(final int op1, final ObservableNumberValue op2) {
        return max(IntegerConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding equal(ObservableUUIDValue op1, ObservableUUIDValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> op1.get().equals(op2.get()));
    }

    public static BooleanBinding equal(ObservableUUIDValue op1, UUID op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> op1.get().equals(op2), op1, UUIDConstant.valueOf(op2));
    }

    public static BooleanBinding notEqual(ObservableUUIDValue op1, ObservableUUIDValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> !op1.get().equals(op2.get()), op1, op2);
    }

    public static BooleanBinding notEqual(ObservableUUIDValue op1, UUID op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> !op1.get().equals(op2), op1, UUIDConstant.valueOf(op2));
    }

    public static BooleanBinding isNull(ObservableUUIDValue value) {
        if (value == null) {
            throw new NullPointerException("Operand cannot be null.");
        }
        
        return new BooleanBinding(() -> value.get() == null, value);
    }

    public static BooleanBinding isNotNull(ObservableUUIDValue value) {
        if (value == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        return isNull(value).not();
    }

    // boolean

     private static class BooleanAndBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;
        private final ObservableBooleanValue op2;
        private final InvalidationListener observer;

        public BooleanAndBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {
            this.op1 = op1;
            this.op2 = op2;

            observer = new ShortCircuitAndInvalidator(this);

            op1.addListener(observer);
            op2.addListener(observer);
        }


        @Override
        public void dispose() {
            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {
            return op1.get() && op2.get();
        }

        @Override
        public ObservableList<?> getDependencies() {
            return new ImmutableObservableList<>(op1, op2);
        }
    }

    private static class ShortCircuitAndInvalidator implements InvalidationListener {

        private final WeakReference<BooleanAndBinding> ref;

        private ShortCircuitAndInvalidator(BooleanAndBinding binding) {
            assert binding != null;
            ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {
            final BooleanAndBinding binding = ref.get();
            if (binding == null) {
                observable.removeListener(this);
            } else {
                // short-circuit invalidation. This BooleanBinding becomes
                // only invalid if the first operator changes or the
                // first parameter is true.
                if ((binding.op1.equals(observable) || (binding.isValid() && binding.op1.get()))) {
                    binding.invalidate();
                }
            }
        }

    }

    public static BooleanBinding and(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanAndBinding(op1, op2);
    }

    private static class BooleanOrBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;
        private final ObservableBooleanValue op2;
        private final InvalidationListener observer;

        public BooleanOrBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {
            this.op1 = op1;
            this.op2 = op2;
            observer = new ShortCircuitOrInvalidator(this);
            op1.addListener(observer);
            op2.addListener(observer);
        }


        @Override
        public void dispose() {
            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {
            return op1.get() || op2.get();
        }

        @Override
        public ObservableList<?> getDependencies() {
            return new ImmutableObservableList<>(op1, op2);
        }
    }

    private static class ShortCircuitOrInvalidator implements InvalidationListener {

        private final WeakReference<BooleanOrBinding> ref;

        private ShortCircuitOrInvalidator(BooleanOrBinding binding) {
            assert binding != null;
            ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {
            final BooleanOrBinding binding = ref.get();
            if (binding == null) {
                observable.removeListener(this);
            } else {
                // short circuit invalidation. This BooleanBinding becomes
                // only invalid if the first operator changes or the
                // first parameter is false.
                if ((binding.op1.equals(observable) || (binding.isValid() && !binding.op1.get()))) {
                    binding.invalidate();
                }
            }
        }

    }

    public static BooleanBinding or(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanOrBinding(op1, op2);
    }

    public static BooleanBinding not(final ObservableBooleanValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }
        
        return new BooleanBinding(() -> !op.get(), op);
    }

    public static BooleanBinding equal(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> op1.get() == op2.get(), op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        
        return new BooleanBinding(() -> op1.get() != op2.get(), op1, op2);
    }

    // String

    public static StringExpression convert(ObservableValue<?> observableValue) {
        return StringFormatter.convert(observableValue);
    }

    public static StringExpression concat(Object... args) {
        return StringFormatter.concat(args);
    }

    public static StringExpression format(String format, Object... args) {
        return StringFormatter.format(format, args);
    }

    public static StringExpression format(Locale locale, String format,
            Object... args) {
        return StringFormatter.format(locale, format, args);
    }

    private static String getStringSafe(String value) {
        return value == null ? "" : value;
    }

    private static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);
        
        return new BooleanBinding(() -> getStringSafe(op1.get()).equals(getStringSafe(op2.get())), dependencies);
    }

    public static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2) {
        return equal(op1, op2, op1, op2);
    }

    public static BooleanBinding equal(final ObservableStringValue op1, String op2) {
        return equal(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equal(String op1, final ObservableStringValue op2) {
        return equal(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding(() -> !getStringSafe(op1.get()).equals(getStringSafe(op2.get())), dependencies);
    }

    public static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return notEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableStringValue op1, String op2) {
        return notEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqual(String op1, final ObservableStringValue op2) {
        return notEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);
        return new BooleanBinding(() -> getStringSafe(op1.get()).equalsIgnoreCase(getStringSafe(op2.get())), dependencies);
    }

    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {
        return equalIgnoreCase(op1, op2, op1, op2);
    }

    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, String op2) {
        return equalIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equalIgnoreCase(String op1, final ObservableStringValue op2) {
        return equalIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);
        return new BooleanBinding(() -> !getStringSafe(op1.get()).equalsIgnoreCase(getStringSafe(op2.get())), dependencies);
    }

    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {
        return notEqualIgnoreCase(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, String op2) {
        return notEqualIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqualIgnoreCase(String op1, final ObservableStringValue op2) {
        return notEqualIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding(() -> getStringSafe(op1.get()).compareTo(getStringSafe(op2.get())) > 0, dependencies);
    }

    public static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2) {
        return greaterThan(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThan(final ObservableStringValue op1, String op2) {
        return greaterThan(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThan(String op1, final ObservableStringValue op2) {
        return greaterThan(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        return greaterThan(op2, op1, dependencies);
    }

    public static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2) {
        return lessThan(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThan(final ObservableStringValue op1, String op2) {
        return lessThan(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThan(String op1, final ObservableStringValue op2) {
        return lessThan(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);
        return new BooleanBinding(() -> getStringSafe(op1.get()).compareTo(getStringSafe(op2.get())) >= 0, dependencies);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, String op2) {
        return greaterThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding greaterThanOrEqual(String op1, final ObservableStringValue op2) {
        return greaterThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2, final Observable... dependencies) {
        return greaterThanOrEqual(op2, op1, dependencies);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return lessThanOrEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, String op2) {
        return lessThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    public static BooleanBinding lessThanOrEqual(String op1, final ObservableStringValue op2) {
        return lessThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    public static IntegerBinding length(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }
        
        return new IntegerBinding(() -> getStringSafe(op.get()).length(), op);
    }

    public static BooleanBinding isEmpty(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }

        return new BooleanBinding(() -> getStringSafe(op.get()).isEmpty(), op);
    }

    public static BooleanBinding isNotEmpty(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }

        return new BooleanBinding(() -> !getStringSafe(op.get()).isEmpty(), op);
    }

    // Object
    // =================================================================================================================

    private static BooleanBinding equal(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);
        
        return new BooleanBinding(() -> op1.get() == null ? op2.get() == null : op1.get().equals(op2.get()), dependencies);
    }

    public static BooleanBinding equal(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2) {
        return equal(op1, op2, op1, op2);
    }

    public static BooleanBinding equal(final ObservableObjectValue<?> op1, Object op2) {
        return equal(op1, ObjectConstant.valueOf(op2), op1);
    }

    public static BooleanBinding equal(Object op1, final ObservableObjectValue<?> op2) {
        return equal(ObjectConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding(() -> op1.get() == null ? op2.get() != null : !op1.get().equals(op2.get()), dependencies);
    }

    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2) {
        return notEqual(op1, op2, op1, op2);
    }

    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, Object op2) {
        return notEqual(op1, ObjectConstant.valueOf(op2), op1);
    }

    public static BooleanBinding notEqual(Object op1, final ObservableObjectValue<?> op2) {
        return notEqual(ObjectConstant.valueOf(op1), op2, op2);
    }

    public static BooleanBinding isNull(final ObservableObjectValue<?> op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }
        
        return new BooleanBinding(() -> op.get() == null, op);
    }

    public static BooleanBinding isNotNull(final ObservableObjectValue<?> op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        return new BooleanBinding(() -> op.get() != null, op);
    }
}