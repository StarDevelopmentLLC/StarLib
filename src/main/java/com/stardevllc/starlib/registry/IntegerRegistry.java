package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.Map;

/**
 * Represents a registry mapping Objects of type {@link V} to Integers
 *
 * @param <V> The object type
 */
public class IntegerRegistry<V> extends Registry<Integer, V> {
    
    /**
     * Creates a new registry
     *
     * @param initialObjects The initial objects
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public IntegerRegistry(Map<Integer, V> initialObjects, KeyNormalizer<Integer> keyNormalizer, KeyRetriever<V, Integer> keyRetriever, KeyGenerator<V, Integer> keyGenerator, KeySetter<Integer, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }
    
    /**
     * Creates a blank registry
     */
    public IntegerRegistry() {
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
        for (RegisterListener<Integer, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<Integer, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }
    
    /**
     * IntegerRegistry Builder
     *
     * @param <V> Value type
     */
    public static class Builder<V> extends Registry.Builder<Integer, V, IntegerRegistry<V>, Builder<V>> {
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
        public IntegerRegistry<V> build() {
            IntegerRegistry<V> registry = new IntegerRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
            
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
