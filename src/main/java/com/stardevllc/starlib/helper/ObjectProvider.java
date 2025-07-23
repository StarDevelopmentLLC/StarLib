package com.stardevllc.starlib.helper;

import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starlib.factory.IFactory;

import java.util.function.Supplier;

public final class ObjectProvider<T> {
    private T instance;
    private Supplier<T> supplier;
    private IFactory<T, ?> factory;
    private IBuilder<T, ?> builder;
    
    public ObjectProvider() {}
    
    public ObjectProvider(ObjectProvider<T> provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.supplier = provider.supplier;
            this.factory = provider.factory;
            this.builder = provider.builder;
        }
    }
    
    public ObjectProvider(T instance) {
        this.instance = instance;
    }
    
    public ObjectProvider(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    
    public ObjectProvider(IFactory<T, ?> factory) {
        this.factory = factory;
    }
    
    public ObjectProvider(IBuilder<T, ?> builder) {
        this.builder = builder;
    }
    
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
    
    public boolean isEmpty() {
        return this.instance == null && this.factory == null && this.builder == null && this.supplier == null;
    }
    
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
    
    public T get() {
        return get(null);
    }
    
    public T getInstance() {
        return this.instance;
    }
    
    public Supplier<T> getSupplier() {
        return supplier;
    }
    
    public IFactory<T, ?> getFactory() {
        return this.factory;
    }
    
    public IBuilder<T, ?> getBuilder() {
        return this.builder;
    }
    
    public ObjectProvider<T> setInstance(T instance) {
        this.instance = instance;
        this.supplier = null;
        this.builder = null;
        this.factory = null;
        return this;
    }
    
    public ObjectProvider<T> setSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
        this.instance = null;
        this.builder = null;
        this.factory = null;
        return this;
    }
    
    public ObjectProvider<T> setFactory(IFactory<T, ?> factory) {
        this.factory = factory;
        this.instance = null;
        this.supplier = null;
        this.builder = null;
        return this;
    }
    
    public ObjectProvider<T> setBuilder(IBuilder<T, ?> builder) {
        this.builder = builder;
        this.instance = null;
        this.supplier = null;
        this.factory = null;
        return this;
    }
}