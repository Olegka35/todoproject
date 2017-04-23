package todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import todo.domain.Task;
import todo.domain.User;
import todo.service.TaskService;
import org.springframework.web.bind.annotation.*;
import todo.service.UserService;

import java.util.List;

/**
 * Created by Олег on 21.04.2017.
 */


@org.springframework.web.bind.annotation.RestController
public class RestController {
    TaskService taskService;
    UserService userService;

    public TaskService getTaskService() {
        return taskService;
    }
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    public UserService getUserService() {
        return userService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks", produces = "application/json")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> taskList = taskService.getTaskList();

        if(taskList.isEmpty())
            return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}", produces = "application/json")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int id) {
        Task task = taskService.getById(id);
        if(task == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks/add", consumes = "application/json")
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {
        List<User> userList = userService.getAllUsers();
        System.out.println(userList);
        User reqUser = null;
        for(User user: userList)
            if(user.getID() == task.getUserID()) {
                reqUser = user;
                break;
            }
        task.setUser(reqUser);
        taskService.add(task);
        return new ResponseEntity<Task>(task, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/delete/{id}")
    public ResponseEntity<Task> deleteTaskByID(@PathVariable("id") int id) {
        if(taskService.getById(id) == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
        taskService.delete(id);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

}