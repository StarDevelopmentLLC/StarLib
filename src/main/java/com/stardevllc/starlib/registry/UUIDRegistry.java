package com.stardevllc.starlib.registry;

import com.stardevllc.starlib.registry.functions.*;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a registry mapping Objects of type {@link V} to UUIDs
 *
 * @param <V> The object type
 */
public class UUIDRegistry<V> extends Registry<UUID, V> {
    
    /**
     * Creates a new registry
     *
     * @param initialObjects The initial objects
     * @param keyNormalizer  The key normalizer
     * @param keyRetriever   The key retriever
     * @param keyGenerator   The key generator
     * @param keySetter      The key setter
     */
    public UUIDRegistry(Map<UUID, V> initialObjects, KeyNormalizer<UUID> keyNormalizer, KeyRetriever<V, UUID> keyRetriever, KeyGenerator<V, UUID> keyGenerator, KeySetter<UUID, V> keySetter) {
        super(initialObjects, keyNormalizer, keyRetriever, keyGenerator, keySetter);
    }
    
    /**
     * Creates a blank registry
     */
    public UUIDRegistry() {
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
        for (RegisterListener<UUID, V> registerListener : this.registerListeners) {
            builder.addRegisterListener(registerListener);
        }
        
        for (UnregisterListener<UUID, V> unregisterListener : this.unregisterListeners) {
            builder.addUnregisterListener(unregisterListener);
        }
        return builder;
    }
    
    /**
     * UUIDRegistry Builder
     *
     * @param <V> Value type
     */
    public static class Builder<V> extends Registry.Builder<UUID, V, UUIDRegistry<V>, Builder<V>> {
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
        public UUIDRegistry<V> build() {
            UUIDRegistry<V> registry = new UUIDRegistry<>(this.objects, this.keyNormalizer, this.keyRetriever, this.keyGenerator, this.keySetter);
            
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