package com.stardevllc;

import com.stardevllc.builder.IBuilder;
import com.stardevllc.factory.IFactory;

public final class ObjectProvider<T> {
    private T instance;
    private IFactory<T, ?> factory;
    private IBuilder<T, ?> builder;
    
    public ObjectProvider() {}
    
    public ObjectProvider(ObjectProvider<T> provider) {
        if (provider != null) {
            this.instance = provider.instance;
            this.factory = provider.factory;
            this.builder = provider.builder;
        }
    }
    
    public ObjectProvider(T instance) {
        this.instance = instance;
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
            this.factory = provider.factory;
            this.builder = provider.builder;
        } else {
            this.instance = null;
            this.factory = null;
            this.builder = null;
        }
        
        return this;
    }
    
    public boolean isEmpty() {
        return this.instance == null && this.factory == null && this.builder == null;
    }
    
    public T get(T defaultValue) {
        if (instance != null) {
            return instance;
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
    
    public IFactory<T, ?> getFactory() {
        return this.factory;
    }
    
    public IBuilder<T, ?> getBuilder() {
        return this.builder;
    }
    
    public ObjectProvider<T> setInstance(T instance) {
        this.instance = instance;
        this.builder = null;
        this.factory = null;
        return this;
    }
    
    public ObjectProvider<T> setFactory(IFactory<T, ?> factory) {
        this.factory = factory;
        this.instance = null;
        this.builder = null;
        return this;
    }
    
    public ObjectProvider<T> setBuilder(IBuilder<T, ?> builder) {
        this.builder = builder;
        this.instance = null;
        this.factory = null;
        return this;
    }
}