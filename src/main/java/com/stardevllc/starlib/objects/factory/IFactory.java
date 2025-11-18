package com.stardevllc.starlib.objects.factory;

import com.stardevllc.starlib.objects.builder.IBuilder;

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
     *
     * @param parameters The parameters for the object
     * @return The created object
     */
    T create(Object[] parameters);
    
    /**
     * Creates a new empty object
     *
     * @return The created object
     */
    default T create() {
        return create(null);
    }
    
    /**
     * Called before creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F preCreate(BiConsumer<F, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    /**
     * Called before creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F preCreate(Consumer<F> consumer) {
        return preCreate((factory, args) -> consumer.accept(factory));
    }
    
    /**
     * Called after creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
    default F postCreate(BiConsumer<T, Object[]> consumer) {
        //No-Op - Up to factory implementation to support
        return self();
    }
    
    /**
     * Called after creation is finalized
     *
     * @param consumer The consumer
     * @return This factory
     */
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
    
    /**
     * A container that either holds an instance of the type or a factory instance, but not both
     *
     * @param <T> The element type
     */
    class Container<T> {
        private T instance;
        private IFactory<T, ?> factory;
        
        /**
         * Constructs a blank container
         */
        public Container() {
        }
        
        /**
         * Copies the container elements to a new one
         *
         * @param container The container to copy from
         */
        public Container(Container<T> container) {
            if (container != null) {
                this.instance = container.instance;
                this.factory = container.factory;
            }
        }
        
        /**
         * Creates a new container based on an instance
         *
         * @param instance The instance
         */
        public Container(T instance) {
            this.instance = instance;
        }
        
        /**
         * Creates a new container based on a factory instance
         *
         * @param factory The factory
         */
        public Container(IFactory<T, ?> factory) {
            this.factory = factory;
        }
        
        /**
         * Returns an instance of the type, using either the provided instance or the factory
         *
         * @param defaultValue The default value if none is found
         * @return The value set, created or the default
         */
        public T get(T defaultValue) {
            if (instance != null) {
                return instance;
            }
            
            if (factory != null) {
                return factory.create();
            }
            
            return defaultValue;
        }
        
        /**
         * Gets an instance without using a default
         *
         * @return The provided instance, a built instance or null
         */
        public T get() {
            return get(null);
        }
        
        /**
         * Checks to see if this container is empty
         *
         * @return If the container is empty
         */
        public boolean isEmpty() {
            return this.instance == null && this.factory == null;
        }
        
        /**
         * Sets the instance of this container and unsets the factory
         *
         * @param instance The instance
         * @return This container object
         */
        public Container<T> set(T instance) {
            this.factory = null;
            this.instance = instance;
            return this;
        }
        
        /**
         * Sets the factory of this container and unsets the instance
         *
         * @param factory The factory
         * @return This container object
         */
        public Container<T> set(IFactory<T, ?> factory) {
            this.instance = null;
            this.factory = factory;
            return this;
        }
        
        /**
         * Gets the instance set directly
         *
         * @return The instance
         */
        public T getInstance() {
            return instance;
        }
        
        /**
         * Gets the factory directly
         *
         * @return The factory
         */
        public IFactory<T, ?> getFactory() {
            return factory;
        }
    }
}