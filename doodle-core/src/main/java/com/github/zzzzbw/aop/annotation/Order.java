package com.github.zzzzbw.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aop顺序
 *
 * @author zzzzbw
 * @since 2018/6/26 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    /**
     * aop顺序,值越大越先执行
     */
    int value() default 0;
}
