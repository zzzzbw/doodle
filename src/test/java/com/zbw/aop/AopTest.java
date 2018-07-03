package com.zbw.aop;

import com.zbw.bean.DoodleController;
import com.zbw.core.BeanContainer;
import com.zbw.ioc.Ioc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by zbw on 2018/6/21.
 */
@Slf4j
public class AopTest {

    @Test
    public void doAop() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.zbw");
        new Aop().doAop();
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
    }

}
