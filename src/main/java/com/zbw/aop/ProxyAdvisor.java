package com.zbw.aop;

import com.zbw.aop.advice.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理通知类
 *
 * @author zbw
 * @since 2018/6/20 23:40
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProxyAdvisor {

    /**
     * 通知
     */
    private Advice advice;

    /**
     * AspectJ表达式切点匹配器
     */
    private ProxyPointcut pointcut;

    /**
     * 执行代理方法
     */
    public Object doProxy(Object target, Class<?> targetClass, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!pointcut.matches(method)) {
            return proxy.invokeSuper(target, args);
        }
        Object result = null;
        if (advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try {
            //执行目标类的方法
            result = proxy.invokeSuper(target, args);
            if (advice instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        } catch (Exception e) {
            if (advice instanceof ThrowsAdvice) {
                ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }
}
