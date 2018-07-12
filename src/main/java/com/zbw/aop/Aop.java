package com.zbw.aop;

import com.zbw.aop.advice.Advice;
import com.zbw.aop.annotation.Aspect;
import com.zbw.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * Aop执行器
 *
 * @author zbw
 * @since 2018/6/20 17:43
 */
@Slf4j
public class Aop {

    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        beanContainer.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .map(this::createProxyAdvisor)
                .forEach(proxyAdvisor -> beanContainer.getClasses()
                        .stream()
                        .filter(target -> !Advice.class.isAssignableFrom(target))
                        .filter(target -> !target.isAnnotationPresent(Aspect.class))
                        .forEach(target -> {
                            if (proxyAdvisor.getPointcut().matches(target)) {
                                Object proxyBean = ProxyCreator.createProxy(target, proxyAdvisor);
                                beanContainer.addBean(target, proxyBean);
                            }
                        }));
    }

    /**
     * 通过Aspect切面类创建代理通知类
     *
     * @param aspectClass Aspect切面类
     * @return 代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointcut);
    }
}
