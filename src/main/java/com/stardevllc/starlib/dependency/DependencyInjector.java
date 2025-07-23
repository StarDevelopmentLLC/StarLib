package com.stardevllc.starlib.dependency;

import java.util.Set;

public interface DependencyInjector {
    static DependencyInjector create() {
        return new DependencyInjectorImpl();
    }
    
    <I> I inject(I object);
    <T, I extends T> I setInstance(Class<T> clazz, I instance);
    <I> I setInstance(I instance);
    
    Set<DependencyInjector> getParentInjectors();
    
    void addParentInjector(DependencyInjector injector);
}