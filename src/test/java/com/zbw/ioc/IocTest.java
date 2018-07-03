package com.zbw.ioc;

import com.zbw.bean.DoodleController;
import com.zbw.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by zbw on 2018/6/21.
 */
@Slf4j
public class IocTest {

    @Test
    public void doIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.zbw");
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
    }

}
