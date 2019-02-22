package com.zbw.controller;

import com.zbw.core.annotation.Controller;
import com.zbw.mvc.annotation.RequestMapping;
import com.zbw.mvc.annotation.ResponseBody;

/**
 * @author zbw
 * @since 2019/2/22 14:07
 */
@Controller
@RequestMapping
public class HelloController {

    @RequestMapping(value = "hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

}
