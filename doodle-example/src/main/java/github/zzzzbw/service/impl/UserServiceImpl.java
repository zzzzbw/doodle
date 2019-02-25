package github.zzzzbw.service.impl;

import com.github.zzzzbw.core.annotation.Service;
import github.zzzzbw.dao.UserDao;
import com.github.zzzzbw.ioc.annotation.Autowired;
import github.zzzzbw.model.User;
import github.zzzzbw.service.UserService;

import java.util.List;

/**
 * @author zzzzbw
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
