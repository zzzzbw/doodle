package com.zbw.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 代理类创建器
 *
 * @author zbw
 * @since 2018/6/21 12:12
 */
public final class ProxyCreator {

    /**
     * 创建代理类
     *
     * @param targetClass  目标类
     * @param proxyAdvisor 代理通知
     * @return 代理类
     */
    public static Object createProxy(Class<?> targetClass, ProxyAdvisor proxyAdvisor) {
        return Enhancer.create(targetClass,
                (MethodInterceptor) (target, method, args, proxy) ->
                        proxyAdvisor.doProxy(target, targetClass, method, args, proxy));
    }
}
