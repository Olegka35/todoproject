package todo.dao;

import todo.domain.Task;

import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */
public interface TaskDao {
    public void add(Task task);
    public void update(Task task);
    public void delete(Integer id);
    public List<Task> getTaskList();
    public Task getById(int id);
    public List<Task> getUserTasks(Integer userID);

}