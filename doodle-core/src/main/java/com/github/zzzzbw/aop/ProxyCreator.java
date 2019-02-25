package com.github.zzzzbw.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理类创建器
 *
 * @author zzzzbw
 * @since 2018/6/21 12:12
 */
public final class ProxyCreator {

    /**
     * 创建代理类
     *
     * @param targetClass 目标类
     * @param proxyList   代理通知列表
     * @return 代理类
     */
    public static Object createProxy(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
        return Enhancer.create(targetClass, new AdviceMethodInterceptor(targetClass, proxyList));
    }

    /**
     * cglib MethodInterceptor实现类
     */
    private static class AdviceMethodInterceptor implements MethodInterceptor {

        /**
         * 目标类
         */
        private final Class<?> targetClass;

        /**
         * 代理通知列表
         */
        private List<ProxyAdvisor> proxyList;

        public AdviceMethodInterceptor(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
            this.targetClass = targetClass;
            this.proxyList = proxyList;
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return new AdviceChain(targetClass, target, method, args, proxy, proxyList).doAdviceChain();
        }
    }
}
