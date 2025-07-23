package com.stardevllc.starlib.dependency;

import com.stardevllc.starlib.helper.ObjectProvider;

import java.util.Set;

public interface DependencyInjector {
    static DependencyInjector create() {
        return new DependencyInjectorImpl();
    }
    
    <I> I inject(I object);
    
    <T, I extends T> I setInstance(Class<T> clazz, I instance);
    <I> I setInstance(I instance);
    
    <T, I extends T> ObjectProvider<I> setProvider(Class<T> clazz, ObjectProvider<I> provider);
    
    Set<DependencyInjector> getParentInjectors();
    void addParentInjector(DependencyInjector injector);
}