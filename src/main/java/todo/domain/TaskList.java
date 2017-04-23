package todo.domain;

import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */
public class TaskList {
    private List<Task> taskList;
    private String login;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}