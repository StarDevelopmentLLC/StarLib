package com.stardevllc.starlib.helper;

import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.factory.IFactory;

import java.util.function.Supplier;

/**
 * Represents a provider of an object using multiple ways to represent an object
 *
 * @param <T> The object type
 */
public final class ObjectProvider<T> {
    private T instance;
    private Supplier<T> supplier;
    private IFactory<T, ?> factory;
    private IBuilder<T, ?> builder;
    
    /**
     * Creates an empty  provider
     */
    public ObjectProvider() {
    }
    
    /**
     * Duplicates a provider
     *
     * @param provider The provider to duplicate
     */
    public ObjectProvider(ObjectProvider<T> provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.supplier = provider.supplier;
            this.factory = provider.factory;
            this.builder = provider.builder;
        }
    }
    
    /**
     * Creates a provider with a direct instance
     *
     * @param instance The instance
     */
    public ObjectProvider(T instance) {
        this.instance = instance;
    }
    
    /**
     * Creates a provider with a supplier
     *
     * @param supplier The supplier
     */
    public ObjectProvider(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    /**
     * Creates a provider with a factory
     *
     * @param factory The factory
     */
    public ObjectProvider(IFactory<T, ?> factory) {
        this.factory = factory;
    }
    
    /**
     * Creates a provider with a builder
     *
     * @param builder The builder
     */
    public ObjectProvider(IBuilder<T, ?> builder) {
        this.builder = builder;
    }
    
    /**
     * Overrides the provider with the new one
     *
     * @param provider The provider
     * @return This provider
     */
    public ObjectProvider<T> setProvider(ObjectProvider<T> provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.supplier = provider.supplier;
            this.factory = provider.factory;
            this.builder = provider.builder;
        } else {
            this.instance = null;
            this.supplier = null;
            this.factory = null;
            this.builder = null;
        }
        
        return this;
    }
    
    /**
     * Checks to see if this provider is empty
     *
     * @return Empty state
     */
    public boolean isEmpty() {
        return this.instance == null && this.factory == null && this.builder == null && this.supplier == null;
    }
    
    /**
     * Gets or creates a value and if not, then returns the default
     *
     * @param defaultValue The default value (can be null)
     * @return The value (can be null)
     */
    public T get(T defaultValue) {
        if (instance != null) {
            return instance;
        }
        
        if (supplier != null) {
            return supplier.get();
        }
        
        if (builder != null) {
            return builder.build();
        }
        
        if (factory != null) {
            return factory.create();
        }
        
        return defaultValue;
    }
    
    /**
     * Same as {@link #get(Object)} but null default
     *
     * @return {@link #get(Object)} as null param
     */
    public T get() {
        return get(null);
    }
    
    /**
     * Gets the direct instance
     *
     * @return The instance
     */
    public T getInstance() {
        return this.instance;
    }
    
    /**
     * Gets the direct supplier
     *
     * @return The supplier
     */
    public Supplier<T> getSupplier() {
        return supplier;
    }
    
    /**
     * Gets the direct factory
     *
     * @return The factory
     */
    public IFactory<T, ?> getFactory() {
        return this.factory;
    }
    
    /**
     * Gets the direct builder
     *
     * @return The builder
     */
    public IBuilder<T, ?> getBuilder() {
        return this.builder;
    }
    
    /**
     * Sets the instance, unsetting others
     *
     * @param instance The instance
     * @return This provider
     */
    public ObjectProvider<T> set(T instance) {
        this.instance = instance;
        this.supplier = null;
        this.builder = null;
        this.factory = null;
        return this;
    }
    
    /**
     * Sets the supplier, unsetting others
     *
     * @param supplier The supplier
     * @return This provider
     */
    public ObjectProvider<T> set(Supplier<T> supplier) {
        this.supplier = supplier;
        this.instance = null;
        this.builder = null;
        this.factory = null;
        return this;
    }
    
    /**
     * Sets the factory, unsetting others
     *
     * @param factory The factory
     * @return This provider
     */
    public ObjectProvider<T> set(IFactory<T, ?> factory) {
        this.factory = factory;
        this.instance = null;
        this.supplier = null;
        this.builder = null;
        return this;
    }
    
    /**
     * Sets the builder, unsetting others
     *
     * @param builder The builder
     * @return This provider
     */
    public ObjectProvider<T> set(IBuilder<T, ?> builder) {
        this.builder = builder;
        this.instance = null;
        this.supplier = null;
        this.factory = null;
        return this;
    }
}