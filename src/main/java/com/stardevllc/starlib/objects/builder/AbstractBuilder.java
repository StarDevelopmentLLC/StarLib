package com.stardevllc.starlib.objects.builder;

import java.util.function.Consumer;

/**
 * This is an optional class that can be used to provide a base implementation of the consumers seen in IBuilder
 *
 * @param <T> The type that is being built
 * @param <B> The builder type (For recursive generics)
 */
public abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> implements IBuilder<T, B> {
    
    protected Consumer<B> prebuildConsumer;
    protected Consumer<T> postbuildConsumer;
    
    protected AbstractBuilder() {
    }
    
    protected AbstractBuilder(B builder) {
        this.prebuildConsumer = builder.prebuildConsumer;
        this.postbuildConsumer = builder.postbuildConsumer;
    }
    
    @Override
    public B preBuild(Consumer<B> consumer) {
        this.prebuildConsumer = consumer;
        return self();
    }
    
    @Override
    public B postBuild(Consumer<T> consumer) {
        this.postbuildConsumer = consumer;
        return self();
    }
    
    @Override
    public Consumer<B> getPrebuildConsumer() {
        return prebuildConsumer;
    }
    
    @Override
    public Consumer<T> getPostbuildConsumer() {
        return postbuildConsumer;
    }
    
    public abstract T newInstance();
    
    @Override
    public T build() {
        if (this.prebuildConsumer != null) {
            this.prebuildConsumer.accept(self());
        }
        
        T object = newInstance();
        
        if (this.postbuildConsumer != null) {
            this.postbuildConsumer.accept(object);
        }
        
        return object;
    }
    
    @Override
    public abstract B clone();
}
