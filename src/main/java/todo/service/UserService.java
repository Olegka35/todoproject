package todo.service;

import todo.domain.User;

import java.util.List;

/**
 * Created by Олег on 30.03.2017.
 */
public interface UserService {
    public void add(User user);
    public void update(User user);
    public void delete(Integer id);
    public User getById(int id);
    public User getUser(String login);
    public List<User> getAllUsers();
}
