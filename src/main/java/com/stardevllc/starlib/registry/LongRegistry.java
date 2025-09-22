package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.Map;

/**
 * Represents a registry mapping Objects of type {@link V} to Longs
 *
 * @param <V> The object type
 */
public class LongRegistry<V> extends Registry<Long, V> {
    
    /**
     * Creates a new registry
     *
     * @param initialObjects The initial objects
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public LongRegistry(Map<Long, V> initialObjects, KeyNormalizer<Long> keyNormalizer, KeyRetriever<V, Long> keyRetriever, KeyGenerator<V, Long> keyGenerator, KeySetter<Long, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }
    
    /**
     * Creates a blank registry
     */
    public LongRegistry() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Builder<V> toBuilder() {
        Builder<V> builder = new Builder<>();
        builder.keyNormalizer(this.keyNormalizer);
        builder.keyRetriever(this.keyRetriever);
        builder.keyGenerator(this.keyGenerator);
        builder.keySetter(this.keySetter);
        for (RegisterListener<Long, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<Long, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }
    
    /**
     * LongRegistry Builder
     *
     * @param <V> Value type
     */
    public static class Builder<V> extends Registry.Builder<Long, V, LongRegistry<V>, Builder<V>> {
        /**
         * Default constructor
         */
        public Builder() {
        }
        
        /**
         * Copy constructor
         *
         * @param builder Builder to copy
         */
        public Builder(Builder<V> builder) {
            super(builder);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public LongRegistry<V> build() {
            LongRegistry<V> registry = new LongRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
            
            registerListeners.forEach(registry::addRegisterListener);
            unregisterListeners.forEach(registry::addUnregisterListener);
            
            return registry;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Builder<V> clone() {
            return new Builder<>(this);
        }
    }
}