package com.stardevllc.starlib.dependency;

import java.lang.annotation.*;

/**
 * Can be applied to fields and types as markers for the DependencyInjector
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface Inject {}