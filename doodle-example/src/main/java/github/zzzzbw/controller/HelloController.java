package github.zzzzbw.controller;

import com.github.zzzzbw.core.annotation.Controller;
import com.github.zzzzbw.mvc.annotation.RequestMapping;
import com.github.zzzzbw.mvc.annotation.ResponseBody;

/**
 * @author zzzzbw
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
