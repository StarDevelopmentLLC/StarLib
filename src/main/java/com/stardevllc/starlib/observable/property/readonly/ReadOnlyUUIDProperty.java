package com.stardevllc.starlib.observable.property.readonly;

import com.stardevllc.starlib.observable.value.ObservableUUID;

import java.util.UUID;

/**
 * Represents a Read-Only UUID value with an identity
 */
public class ReadOnlyUUIDProperty extends ReadOnlyObjectProperty<UUID> implements ObservableUUID {
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean  The owner
     * @param name  The name
     * @param value The value
     */
    public ReadOnlyUUIDProperty(Object bean, String name, UUID value) {
        this(bean, name);
        this.value = value;
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param bean The owner
     * @param name The name
     */
    public ReadOnlyUUIDProperty(Object bean, String name) {
        super(bean, name, UUID.class);
    }
    
    /**
     * Constructs a ReadOnly Property
     *
     * @param value The value
     */
    public ReadOnlyUUIDProperty(UUID value) {
        this(null, null, value);
    }
    
    /**
     * Constructs a ReadOnly Property
     */
    public ReadOnlyUUIDProperty() {
        this(null, null);
    }
}
