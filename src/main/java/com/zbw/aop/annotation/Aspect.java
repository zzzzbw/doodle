package com.zbw.aop.annotation;

import java.lang.annotation.*;

/**
 * @author zbw
 * @since 2018/6/6 15:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 切点表达式
     */
    String pointcut() default "";

}
