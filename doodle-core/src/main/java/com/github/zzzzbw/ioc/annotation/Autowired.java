package com.github.zzzzbw.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowired
 *
 * @author zzzzbw
 * @since 2018/5/24 18:01
 */
// TODO: 1.构造注入(Constructor Injection)2.设值方法注入(Setter Injection)3.接口注入(Interface Injection)
// TODO: 目前采用接口注入
// TODO: 构造注入会出现循环依赖
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
