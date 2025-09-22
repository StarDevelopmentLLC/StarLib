package com.stardevllc.starlib.builder;

import java.util.function.Consumer;

/**
 * <pre>This represents a Builder. This interface mainly exists to define a contract for all builders by StarDevLLC and optionally other things that may use StarLib
 * It is NOT required to use this interface if you are using StarLib as StarLib is just a loose collection of utilities and not a hard library</pre>
 *
 * @param <T> The type that this Builder builds
 * @param <B> The type of the Builder (For recursive methods) (Not the name, will figure it out)
 */
public interface IBuilder<T, B extends IBuilder<T, B>> extends Cloneable {
    
    /**
     * Builds a new object based on other aspects of this builder.
     * This also calls the pre and post build consumers if they exist
     *
     * @return A new object
     */
    T build();
    
    /**
     * <pre>This must properly copy all fields over to a new instance of this builder.
     * It is recommended to use constructors to achieve this if using this in a hierarchy</pre>
     *
     * @return A copy of this builder
     */
    B clone();
    
    /**
     * Prebuild actions to take. Not all builders will support this
     *
     * @param consumer The consumer for the actions
     * @return A copy of this builder
     */
    default B preBuild(Consumer<B> consumer) {
        //No-op, up to builder implementation to support
        return self();
    }
    
    /**
     * Postbuild actions to take. Not all builders will support this
     *
     * @param consumer The consumer for the actions
     * @return A copy of this builder
     */
    default B postBuild(Consumer<T> consumer) {
        //No-op, up to builder implementation to support
        return self();
    }
    
    /**
     * Returns the consumer for the prebuild actions or null if not set or supported
     *
     * @return The consumer
     */
    default Consumer<B> getPrebuildConsumer() {
        return null;
    }
    
    /**
     * Returns the consumer for the postbuild actions or null if not set or supported
     *
     * @return The consumer
     */
    default Consumer<T> getPostbuildConsumer() {
        return null;
    }
    
    /**
     * <pre>Returns an instance of this builder without having to cast every time.
     * Note when creating methods, use either generic types or the final concrete type if it is a final class</pre>
     *
     * @return this builder
     */
    default B self() {
        return (B) this;
    }
    
    /**
     * A container that either holds an instance of the type or a builder instance, but not both
     *
     * @param <T> The element type
     */
    class Container<T> {
        private T instance;
        private IBuilder<T, ?> builder;
        
        /**
         * Constructs a blank consumer
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
                this.builder = container.builder;
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
         * Creates a new container based on a builder instance
         *
         * @param builder The builder
         */
        public Container(IBuilder<T, ?> builder) {
            this.builder = builder;
        }
        
        /**
         * Returns an instance of the type, using either the provided instance or the builder
         *
         * @param defaultValue The default value if none is found
         * @return The value set, created or the default
         */
        public T get(T defaultValue) {
            if (instance != null) {
                return instance;
            }
            
            if (builder != null) {
                return builder.build();
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
            return this.instance == null && this.builder == null;
        }
        
        /**
         * Sets the instance of this container and unsets the builder
         *
         * @param instance The instance
         * @return This container object
         */
        public Container<T> set(T instance) {
            this.builder = null;
            this.instance = instance;
            return this;
        }
        
        /**
         * Sets the builder of this container and unsets the instance
         *
         * @param builder The builder
         * @return This container object
         */
        public Container<T> set(IBuilder<T, ?> builder) {
            this.instance = null;
            this.builder = builder;
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
         * Gets the builder directly
         *
         * @return The builder
         */
        public IBuilder<T, ?> getBuilder() {
            return builder;
        }
    }
}