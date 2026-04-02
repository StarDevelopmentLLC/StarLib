package com.stardevllc.starlib.objects.factory;

import java.util.function.BiConsumer;

/**
 * This is an optional class that can be used to provide default functionality of the consumers without having to implement yourself
 * @param <T> The built type
 * @param <F> The factory type (For recursive generics)
 */
public abstract class AbstractFactory<T, F extends AbstractFactory<T, F>> implements IFactory<T, F> {
    
    protected BiConsumer<F, Object[]> precreateConsumer;
    protected BiConsumer<T, Object[]> postcreateConsumer;
    
    protected AbstractFactory() {}
    
    protected AbstractFactory(F factory) {
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
}