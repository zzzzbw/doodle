package com.sample.service.impl;

import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.service.UserService;
import com.zbw.core.annotation.Service;
import com.zbw.ioc.annotation.Autowired;

import java.util.List;

/**
 * @author zbw
 * @since 2018/6/1 17:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long id) {
        return userDao.get(id);
    }

    @Override
    public List<User> getUser() {
        return userDao.getAll();
    }

    @Override
    public User addUser(String name) {
        return userDao.save(name);
    }

    @Override
    public int deleteUser(long id) {
        return userDao.delete(id);
    }
}
