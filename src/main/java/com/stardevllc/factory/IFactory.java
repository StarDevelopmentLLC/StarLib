package com.stardevllc.factory;

import com.stardevllc.builder.IBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This is an interface that defines a common contract for Factory types in StarDevLLC projects. 
 * Much like the {@link IBuilder} class, this is not a required interface to use if using StarLib
 * This is meant to be flexible in the implementation and can use multiple different ideas or design principles to achieve things.
 * This does not mean you have to follow the "Factory Design Pattern". 
 * For example, in a personal project, it uses Builders to manage the actual creation of the object, then a Factory to randomize values for that builder without having to bloat the Builder instance, and a Generator to manage generating multiple at the same time
 * 
 * @param <T> The object type
 * @param <F> The factory type
 */
@FunctionalInterface
public interface IFactory<T, F extends IFactory<T, F>> {
    
    /**
     * Creates an object with some parameters passed in. 
     * This is useful if it is variable number of parameters or of different types. 
     * It is up to the {@link IFactory} implementation to hande that.
     * By default, it delegates to {@link IFactory#create()} ignoring the parameters
     * @param parameters The parameters for the object
     * @return The created object
     */
    T create(Object[] parameters);
    
    /**
     * Creates a new empty object
     * @return The created object
     */
    default T create() {
        return create(null);
    }
    
    default F preCreate(BiConsumer<F, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    default F preCreate(Consumer<F> consumer) {
        return preCreate((factory, args) -> consumer.accept(factory));
    }
    
    default F postCreate(BiConsumer<T, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    default F postCreate(Consumer<T> consumer) {
        return postCreate((object, args) -> consumer.accept(object));
    }
    
    /**
     * Returns an instance of this builder without having to cast every time.
     * Note when creating methods, use either generic types or the final concrete type if it is a final class
     *
     * @return this builder
     */
    default F self() {
        return (F) this;
    }
    
    class Container<T> {
        private T instance;
        private IFactory<T, ?> factory;
        
        public Container() {}
        
        public Container(Container<T> container) {
            if (container != null) {
                this.instance = container.instance;
                this.factory = container.factory;
            }
        }
        
        public Container(T instance) {
            this.instance = instance;
        }
        
        public Container(IFactory<T, ?> factory) {
            this.factory = factory;
        }
        
        public T get(T defaultValue) {
            if (instance != null) {
                return instance;
            }
            
            if (factory != null) {
                return factory.create();
            }
            
            return defaultValue;
        }
        
        public T get() {
            return get(null);
        }
        
        public boolean isEmpty() {
            return this.instance == null && this.factory == null;
        }
        
        public Container<T> setInstance(T instance) {
            this.factory = null;
            this.instance = instance;
            return this;
        }
        
        public T getInstance() {
            return instance;
        }
        
        public Container<T> setFactory(IFactory<T, ?> factory) {
            this.instance = null;
            this.factory = factory;
            return this;
        }
        
        public IFactory<T, ?> getFactory() {
            return factory;
        }
    }
}