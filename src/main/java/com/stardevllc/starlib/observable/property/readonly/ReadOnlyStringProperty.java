package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.value.ObservableStringValue;

/**
 * Represents a Read-Only String value with an identity
 */
public class ReadOnlyStringProperty extends ReadOnlyObjectProperty<String> implements ObservableStringValue {
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyStringProperty(Object bean, String name, String value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyStringProperty(Object bean, String name) {
        super(bean, name, String.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyStringProperty(String value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyStringProperty() {
        this(null, null);
    }
}
