package com.stardevllc.starlib.repository;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.id.ID;
import com.stardevllc.starlib.objects.id.impl.StringId;
import com.stardevllc.starlib.time.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Repositories {
    
    private static final Map<ID, IRepository<?, ?>> REPOSITORIES = new HashMap<>();
    
    public static <K, V> IRepository<K, V> getRepository(ID id) {
        return (IRepository<K, V>) REPOSITORIES.get(id);
    }
    
    public static <K, V> void addRepository(IRepository<K, V> repository) {
        if (repository.getId() != null) {
            REPOSITORIES.put(repository.getId(), repository);
        }
    }
    
    public static class RepositoryBuilder<K, V> {
        private Class<K> keyType;
        private Class<V> valueType;
        private String name;
        private ID id;
        private Supplier<Map<K, V>> mapSupplier;
        private EventDispatcher<IRepository.Event> dispatcher;
        private IRepository.TaskSubmitter taskSubmitter;
        private long timeout;
        private Function<K, V> valueFetcher;
        private boolean global;
        
        public RepositoryBuilder(Class<K> keyType, Class<V> valueType) {
            this.keyType = keyType;
            this.valueType = valueType;
        }
        
        public RepositoryBuilder(Class<K> keyType, Class<V> valueType, Supplier<Map<K, V>> mapSupplier) {
            this(keyType, valueType);
            this.mapSupplier = mapSupplier;
        }
        
        public RepositoryBuilder<K, V> withId(ID id) {
            this.id = id;
            return this;
        }
        
        public RepositoryBuilder<K, V> asGlobal() {
            this.global = true;
            return this;
        }
        
        public RepositoryBuilder<K, V> withName(String name) {
            this.name = name;
            return this;
        }
        
        public RepositoryBuilder<K, V> withSupplier(Supplier<Map<K, V>> supplier) {
            this.mapSupplier = supplier;
            return this;
        }
        
        public RepositoryBuilder<K, V> withDispatcher(EventDispatcher<IRepository.Event> dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }
        
        public RepositoryBuilder<K, V> withSubmitter(IRepository.TaskSubmitter taskSubmitter) {
            this.taskSubmitter = taskSubmitter;
            return this;
        }
        
        public RepositoryBuilder<K, V> withTimeout(long millis) {
            this.timeout = millis;
            return this;
        }
        
        public RepositoryBuilder<K, V> withTimeout(long time, TimeUnit unit) {
            return withTimeout(unit.toMillis(time));
        }
        
        public RepositoryBuilder<K, V> withTimeout(long time, java.util.concurrent.TimeUnit unit) {
            return withTimeout(unit.toMillis(time));
        }
        
        public RepositoryBuilder<K, V> withFetcher(Function<K, V> fetcher) {
            this.valueFetcher = fetcher;
            return this;
        }
        
        public IRepository<K, V> build() {
            ID id = null;
            if (this.id == null) {
                if (this.name != null) {
                    id = new StringId(this.name);
                }
            } else {
                id = this.id;
            }
            
            if (this.keyType == null) {
                throw new IllegalArgumentException("keyType cannot be null");
            }
            
            if (this.valueType == null) {
                throw new IllegalArgumentException("valueType cannot be null");
            }
            
            if (this.mapSupplier == null) {
                throw new IllegalArgumentException("mapSupplier cannot be null");
            }
            
            Map<K, V> map = this.mapSupplier.get();
            if (map == null) {
                throw new IllegalStateException("mapSupplier cannot return a null map");
            }
            
            AbstractRepository<K, V> repository = new AbstractRepository<>(this.keyType, this.valueType, map, id, name, valueFetcher, dispatcher, timeout, taskSubmitter) {};
            
            if (this.global) {
                if (id != null && id.isNotEmpty()) {
                    REPOSITORIES.put(repository.getId(), repository);
                }
            }
            return repository;
        }
    }
    
    public static <K, V> RepositoryBuilder<K, V> create(Class<K> keyType, Class<V> valueType, Supplier<Map<K, V>> mapSupplier) {
        return new RepositoryBuilder<>(keyType, valueType, mapSupplier);
    }
    
    public static <K, V> RepositoryBuilder<K, V> create(Class<K> keyType, Class<V> valueType) {
        return new RepositoryBuilder<>(keyType, valueType);
    }
}