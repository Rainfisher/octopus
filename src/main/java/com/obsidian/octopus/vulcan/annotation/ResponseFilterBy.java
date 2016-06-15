package com.obsidian.octopus.vulcan.annotation;

import com.obsidian.octopus.vulcan.filter.ResponseFilter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseFilterBy {

    Class<? extends ResponseFilter> value();

}
