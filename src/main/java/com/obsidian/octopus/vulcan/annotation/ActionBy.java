package com.obsidian.octopus.vulcan.annotation;

import com.obsidian.octopus.vulcan.interceptor.Interceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Alex Chou
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionBy {

    Class<? extends Interceptor>[] interceptors();

}
