package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.property.AbstractReadOnlyProperty;
import com.stardevllc.starlib.observable.value.ObservableDouble;

/**
 * Represents a Read-Only Double value with an identity
 */
public class ReadOnlyDoubleProperty extends AbstractReadOnlyProperty<Double> implements ObservableDouble {
    
    /**
     * The value of the property
     */
    protected double value;
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyDoubleProperty(Object bean, String name, double value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyDoubleProperty(Object bean, String name) {
        super(bean, name, Double.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyDoubleProperty(double value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyDoubleProperty() {
        this(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double get() {
        if (boundValue != null) {
            return boundValue.getValue();
        }
        
        return value;
    }
}
