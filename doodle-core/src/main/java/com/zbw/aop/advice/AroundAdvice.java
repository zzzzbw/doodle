package com.zbw.aop.advice;

/**
 * 环绕通知接口
 *
 * @author zbw
 * @since 2018/6/20 17:41
 */
public interface AroundAdvice extends MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
}
