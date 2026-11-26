package com.stardevllc.starlib.objects.factory;

import com.stardevllc.starlib.objects.builder.IBuilder;

import java.util.function.*;

/**
 * This is an optional class that can be used to provide default functionality of the consumers without having to implement yourself and using a builder implementation for it
 *
 * @param <T> The built type
 * @param <B> The builder type
 * @param <F> The factory type (For recursive generics)
 */
public abstract class AbstractFactory<T, B extends IBuilder<T, B>, F extends AbstractFactory<T, B, F>> implements IFactory<T, F> {
    
    protected BiConsumer<F, Object[]> precreateConsumer;
    protected BiConsumer<T, Object[]> postcreateConsumer;
    
    protected final Supplier<B> builderSupplier;
    
    protected AbstractFactory(Supplier<B> builderSupplier) {
        this.builderSupplier = builderSupplier;
    }
    
    protected AbstractFactory(F factory) {
        this.builderSupplier = factory.builderSupplier;
        this.precreateConsumer = factory.precreateConsumer;
        this.postcreateConsumer = factory.postcreateConsumer;
    }
    
    @Override
    public F preCreate(BiConsumer<F, Object[]> consumer) {
        precreateConsumer = consumer;
        return self();
    }
    
    @Override
    public F postCreate(BiConsumer<T, Object[]> consumer) {
        postcreateConsumer = consumer;
        return self();
    }
    
    protected abstract void applyToBuilder(B builder, Object[] params);
    
    @Override
    public T create(Object[] parameters) {
        if (precreateConsumer != null) {
            precreateConsumer.accept(self(), parameters);
        }
        
        B builder = this.builderSupplier.get();
        
        if (builder == null) {
            throw new IllegalStateException("The builder supplier returned a null builder");
        }
        
        applyToBuilder(builder, parameters);
        
        T object = builder.build();
        
        if (object == null) {
            throw new IllegalStateException("The builder returned a null object");
        }
        
        if (this.postcreateConsumer != null) {
            this.postcreateConsumer.accept(object, parameters);
        }
        
        return object;
    }
    
    @Override
    public abstract F clone();
}