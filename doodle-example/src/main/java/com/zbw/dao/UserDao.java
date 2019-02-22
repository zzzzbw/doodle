package com.zbw.dao;

import com.zbw.model.User;

import java.util.List;

/**
 * @author zbw
 * @since 2018/6/28 10:37
 */
public interface UserDao {
    User get(long id);

    List<User> getAll();

    User save(String name);

    int delete(long id);
}
