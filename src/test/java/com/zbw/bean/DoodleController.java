package com.zbw.bean;

import com.zbw.core.annotation.Controller;
import com.zbw.ioc.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zbw
 * @since 2018/6/21 17:37
 */
@Controller
@Slf4j
public class DoodleController {

    @Autowired
    private DoodleService doodleService;

    public void hello() {
        log.info(doodleService.helloWord());
    }
}
