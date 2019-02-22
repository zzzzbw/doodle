package com.zbw.controller;


import com.zbw.core.annotation.Controller;
import com.zbw.ioc.annotation.Autowired;
import com.zbw.model.User;
import com.zbw.mvc.annotation.RequestMapping;
import com.zbw.mvc.annotation.RequestMethod;
import com.zbw.mvc.annotation.RequestParam;
import com.zbw.mvc.annotation.ResponseBody;
import com.zbw.mvc.bean.ModelAndView;
import com.zbw.service.UserService;
import com.zbw.utils.Result;

import java.util.List;

/**
 * @author zbw
 * @since 2018/5/24 17:40
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> test() {
        return new Result<>("test", 0, "");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getUserList() {
        List<User> list = userService.getUser();
        return new ModelAndView().setView("user_list.jsp").addObject("list", list);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView getUser(@RequestParam(value = "id") long id) {
        User user = userService.getUserById(id);
        return new ModelAndView().setView("user_detail.jsp").addObject("user", user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<User> addUser(@RequestParam(value = "name") String name) {
        User user = userService.addUser(name);
        return new Result<>(user, 0, "");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> deleteUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return new Result<>("success", 0, "");
    }
}
