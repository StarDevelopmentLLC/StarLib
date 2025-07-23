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
    
    default B preBuild(Consumer<B> consumer) {
        //No-op, up to builder implementation to support
        return self();
    }
    
    default B postBuild(Consumer<T> consumer) {
        //No-op, up to builder implementation to support
        return self();
    }
    
    default Consumer<B> getPrebuildConsumer() {
        return null;
    }
    
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
    
    class Container<T> {
        private T instance;
        private IBuilder<T, ?> builder;
        
        public Container() {}
        
        public Container(Container<T> container) {
            if (container != null) {
                this.instance = container.instance;
                this.builder = container.builder;
            }
        }
        
        public Container(T instance) {
            this.instance = instance;
        }
        
        public Container(IBuilder<T, ?> builder) {
            this.builder = builder;
        }
        
        public T get(T defaultValue) {
            if (instance != null) {
                return instance;
            }
            
            if (builder != null) {
                return builder.build();
            }
            
            return defaultValue;
        }
        
        public T get() {
            return get(null);
        }
        
        public boolean isEmpty() {
            return this.instance == null && this.builder == null;
        }
        
        public Container<T> setInstance(T instance) {
            this.builder = null;
            this.instance = instance;
            return this;
        }
        
        public T getInstance() {
            return instance;
        }
        
        public Container<T> setBuilder(IBuilder<T, ?> builder) {
            this.instance = null;
            this.builder = builder;
            return this;
        }
        
        public IBuilder<T, ?> getBuilder() {
            return builder;
        }
    }
}