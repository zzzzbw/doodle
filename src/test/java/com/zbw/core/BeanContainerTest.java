package com.zbw.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by zbw on 2018/6/21.
 */
@Slf4j
public class BeanContainerTest {

    @Test
    public void getAllBean() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.zbw");
        beanContainer.getBeans().forEach(bean -> log.info("class: {}", bean.getClass().getName()));
    }
}
