package github.zzzzbw.dao;

import github.zzzzbw.model.User;

import java.util.List;

/**
 * @author zzzzbw
 * @since 2018/6/28 10:37
 */
public interface UserDao {
    User get(long id);

    List<User> getAll();

    User save(String name);

    int delete(long id);
}
