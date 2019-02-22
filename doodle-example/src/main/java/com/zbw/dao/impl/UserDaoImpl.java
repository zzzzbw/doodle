package com.zbw.dao.impl;


import com.zbw.core.annotation.Repository;
import com.zbw.dao.UserDao;
import com.zbw.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zbw
 * @since 2018/6/28 10:37
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final List<User> USERS = new ArrayList<>();

    static {
        USERS.add(new User(1L, "Tom"));
        USERS.add(new User(2L, "Jerry"));
        USERS.add(new User(3L, "Mark"));
        USERS.add(new User(4L, "Marry"));
    }


    @Override
    public User get(long id) {
        return USERS.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return USERS;
    }

    @Override
    public User save(String name) {
        long id = USERS.stream().mapToLong(User::getId).max().orElse(0L);
        User user = new User(id + 1, name);
        USERS.add(user);
        return user;
    }

    @Override
    public int delete(long id) {
        User user = this.get(id);
        if (null == user) {
            return 0;
        }
        USERS.remove(user);
        return 1;
    }
}
