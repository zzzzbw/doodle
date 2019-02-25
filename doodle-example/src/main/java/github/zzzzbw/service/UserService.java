package github.zzzzbw.service;


import github.zzzzbw.model.User;

import java.util.List;

/**
 * @author zzzzbw
 * @since 2018/5/24 17:41
 */
public interface UserService {
    User getUserById(long id);

    List<User> getUser();

    User addUser(String name);

    int deleteUser(long id);
}
