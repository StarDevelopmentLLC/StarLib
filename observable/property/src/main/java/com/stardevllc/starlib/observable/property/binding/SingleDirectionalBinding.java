package com.stardevllc.starlib.observable.property.binding;

import com.stardevllc.starlib.observable.ChangeListener;
import com.stardevllc.starlib.observable.ObservableValue;
import com.stardevllc.starlib.observable.ReadWriteProperty;
import com.stardevllc.starlib.observable.WritableValue;
import com.stardevllc.starlib.observable.property.writable.ReadWriteDoubleProperty;
import com.stardevllc.starlib.observable.property.writable.ReadWriteFloatProperty;
import com.stardevllc.starlib.observable.property.writable.ReadWriteIntegerProperty;
import com.stardevllc.starlib.observable.property.writable.ReadWriteLongProperty;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class SingleDirectionalBinding<E, T extends ReadWriteProperty<E>> implements ChangeListener<E> {

    private WeakReference<T> boundRef; //Property 1 is the one bound
    private WeakReference<T> targetRef; //Property 2 is the one that the value is being bound to
    private E oldValue;
    private boolean updating;
    private final int cachedHashCode;

    protected SingleDirectionalBinding(Object bound, Object target) {
        this.cachedHashCode = target.hashCode();
    }

    public SingleDirectionalBinding(T bound, T target) {
        this((Object) bound, target);
        this.boundRef = new WeakReference<>(bound);
        this.targetRef = new WeakReference<>(target);
        this.oldValue = bound.getValue();
    }

    @Override
    public void changed(ObservableValue<? extends E> sourceProperty, E oldValue, E newValue) {
        if (!updating) {
            final T bound = boundRef.get();
            final T target = targetRef.get();
            if ((bound == null) || (target == null)) {
                if (target != null) {
                    target.removeListener(this);
                }
            } else {
                try {
                    updating = true;
                    bound.setValue(newValue);
                    bound.getValue();
                    this.oldValue = newValue;
                } catch (RuntimeException e) {
                    try {
                        if (bound == sourceProperty) {
                            bound.setValue(this.oldValue);
                            bound.getValue();
                        }
                    } catch (Exception e2) {
                        e2.addSuppressed(e);
                        unbind(bound, target);
                        throw new RuntimeException("SingleDirectional binding failed with an attempt to restore the source property to the previous value. Removing the binding from the property " + target, e2);
                    }
                    throw new RuntimeException("Bidirectional binding failed, setting to the previous value", e);
                } finally {
                    updating = false;
                }
            }
        }
    }

    private static void checkParameters(Object bound, Object target) {
        Objects.requireNonNull(bound, "Both properties must be specified.");
        Objects.requireNonNull(target, "Both properties must be specified.");
        if (bound == target) {
            throw new IllegalArgumentException("Cannot bind property to itself");
        }
    }

    public static <T> SingleDirectionalBinding bind(ReadWriteProperty<T> bound, ReadWriteProperty<T> target) {
        checkParameters(bound, target);
        SingleDirectionalBinding binding = new SingleDirectionalBinding(bound, target);
        bound.setValue(target.getValue());
        bound.getValue();
        target.addListener(binding);
        return binding;
    }

    public static <T> void unbind(ReadWriteProperty<T> bound, ReadWriteProperty<T> target) {
        checkParameters(bound, target);
        final SingleDirectionalBinding binding = new SingleDirectionalBinding(bound, target);
        target.removeListener(binding);
    }

    public static void unbind(Object bound, Object target) {
        checkParameters(bound, target);
        final SingleDirectionalBinding binding = new SingleDirectionalBinding(bound, target);
        if (target instanceof WritableValue observableValue) {
            observableValue.removeListener(binding);
        }
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteProperty<Integer> bound, ReadWriteIntegerProperty target) {
        return bindNumber(bound, (ReadWriteProperty<Number>) target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteProperty<Long> bound, ReadWriteLongProperty target) {
        return bindNumber(bound, (ReadWriteProperty<Number>) target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteProperty<Float> bound, ReadWriteFloatProperty target) {
        return bindNumber(bound, (ReadWriteProperty<Number>) target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteProperty<Double> bound, ReadWriteDoubleProperty target) {
        return bindNumber(bound, (ReadWriteProperty<Number>) target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteIntegerProperty bound, ReadWriteProperty<Integer> target) {
        return bindNumberObject(bound, target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteLongProperty bound, ReadWriteProperty<Long> target) {
        return bindNumberObject(bound, target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteFloatProperty bound, ReadWriteProperty<Float> target) {
        return bindNumberObject(bound, target);
    }

    public static SingleDirectionalBinding bindNumber(ReadWriteDoubleProperty bound, ReadWriteProperty<Double> target) {
        return bindNumberObject(bound, target);
    }

    private static <T extends Number> SingleDirectionalBinding bindNumberObject(ReadWriteProperty<Number> bound, ReadWriteProperty<T> target) {
        checkParameters(bound, target);

        final SingleDirectionalBinding binding = new SingleDirectionalBinding(target, bound);

        bound.setValue(target.getValue());
        bound.getValue();
        target.addListener(binding);
        return binding;
    }

    private static <T extends Number> SingleDirectionalBinding bindNumber(ReadWriteProperty<T> bound, ReadWriteProperty<Number> target) {
        checkParameters(bound, target);

        final SingleDirectionalBinding binding = new SingleDirectionalBinding(bound, target);

        bound.setValue((T) target.getValue());
        bound.getValue();
        target.addListener(binding);
        return binding;
    }

    protected T getBound() {
        return boundRef.get();
    }

    protected T getTarget() {
        return targetRef.get();
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        final Object propertyA1 = getBound();
        final Object propertyA2 = getTarget();
        if ((propertyA1 == null) || (propertyA2 == null)) {
            return false;
        }

        if (obj instanceof SingleDirectionalBinding otherBinding) {
            final Object propertyB1 = otherBinding.getBound();
            final Object propertyB2 = otherBinding.getTarget();
            if ((propertyB1 == null) || (propertyB2 == null)) {
                return false;
            }

            if (propertyA1 == propertyB1 && propertyA2 == propertyB2) {
                return true;
            }
            return propertyA1 == propertyB2 && propertyA2 == propertyB1;
        }
        return false;
    }
}
