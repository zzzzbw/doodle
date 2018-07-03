package com.sample.controller;

import com.sample.model.User;
import com.sample.service.UserService;
import com.sample.utils.Result;
import com.zbw.core.annotation.Controller;
import com.zbw.ioc.annotation.Autowired;
import com.zbw.mvc.annotation.RequestMapping;
import com.zbw.mvc.annotation.RequestMethod;
import com.zbw.mvc.annotation.RequestParam;
import com.zbw.mvc.annotation.ResponseBody;
import com.zbw.mvc.bean.ModelAndView;

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
