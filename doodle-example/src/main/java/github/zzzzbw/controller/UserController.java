package github.zzzzbw.controller;


import com.github.zzzzbw.util.ValidateUtil;
import github.zzzzbw.model.User;
import github.zzzzbw.service.UserService;
import com.github.zzzzbw.core.annotation.Controller;
import com.github.zzzzbw.ioc.annotation.Autowired;
import com.github.zzzzbw.mvc.annotation.RequestMapping;
import com.github.zzzzbw.mvc.annotation.RequestMethod;
import com.github.zzzzbw.mvc.annotation.RequestParam;
import com.github.zzzzbw.mvc.annotation.ResponseBody;
import com.github.zzzzbw.mvc.bean.ModelAndView;
import github.zzzzbw.utils.Result;

import java.util.List;

/**
 * @author zzzzbw
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
        if (ValidateUtil.isEmpty(name)) {
            return new Result<>(null, -1, "name不能为空");
        }
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
