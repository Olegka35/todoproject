package todo.service.impl;

import todo.dao.UserDao;
import todo.domain.User;
import todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 30.03.2017.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void add(User user) {
        userDao.add(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(Integer id) {
        userDao.delete(id);
    }

    public User getById(int id) {
        return userDao.getById(id);
    }

    public User getUser(String login) {
        return userDao.getUser(login);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


}
