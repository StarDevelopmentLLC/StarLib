package com.stardevllc.dependency;

public interface DependencyInjector {
    static DependencyInjector create() {
        return new DependencyInjectorImpl();
    }
    
    <I> I inject(I object);
    <T, I extends T> I setInstance(Class<T> clazz, I instance);
    <I> I setInstance(I instance);
}