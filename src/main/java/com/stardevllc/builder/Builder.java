package com.stardevllc.builder;

/**
 * <pre>This represents a Builder. This interface mainly exists to define a contract for all builders by StarDevLLC and optionally other things that may use StarLib
 * It is NOT required to use this interface if you are using StarLib as StarLib is just a loose collection of utilities and not a hard library</pre>
 * 
 * @param <T> The type that this Builder builds
 * @param <B> The type of the Builder (For recursive methods) (Not the name, will figure it out)
 */
public interface Builder<T, B extends Builder<T, B>> extends Cloneable {
    
    /**
     * Builds a new object based on other aspects of this builder. 
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
     * <pre>Returns an instance of this builder without having to cast every time.
     * Note when creating methods, use either generic types or the final concrete type if it is a final class</pre>
     *
     * @return this builder
     */
    default B self() {
        return (B) this;
    }
}