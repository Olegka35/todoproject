package todo.service.impl;

import todo.dao.TaskDao;
import todo.domain.Task;
import todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDAO;

    public void add(Task task) {
        taskDAO.add(task);
    }

    public void update(Task task) { taskDAO.update(task); }

    public List<Task> getTaskList() {
        return taskDAO.getTaskList();
    }

    public Task getById(int id) {
        return taskDAO.getById(id);
    }

    public void delete(Integer id) {
        taskDAO.delete(id);
    }

    public List<Task> getUserTasks(Integer userID) {
        return taskDAO.getUserTasks(userID);
    }
}
