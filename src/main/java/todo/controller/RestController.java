package todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import todo.service.OperatorService;

import java.util.List;

/**
 * Created by Олег on 21.04.2017.
 */


/*@org.springframework.web.bind.annotation.RestController
public class RestController {
    private TaskService taskService;

    private OperatorService operatorService;

    @Autowired
    public RestController(TaskService taskService, OperatorService operatorService) {
        this.taskService = taskService;
        this.operatorService = operatorService;
    }


    public TaskService getTaskService() {
        return taskService;
    }
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    public OperatorService getOperatorService() {
        return operatorService;
    }
    public void setOperatorService(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks", produces = "application/json")
    public ResponseEntity<List<Task>> getUserTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = operatorService.getUser(auth.getName());

        List<Task> taskList = taskService.getUserTasks(user.getID());

        if(taskList.isEmpty())
            return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/all", produces = "application/json")
    public ResponseEntity<List<Task>> getAllTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = operatorService.getUser(auth.getName());
        if(!user.getRole().equals("ROLE_ADMIN"))
            return new ResponseEntity<List<Task>>(HttpStatus.FORBIDDEN);

        List<Task> taskList = taskService.getArticleList();

        if(taskList.isEmpty())
            return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}", produces = "application/json")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = operatorService.getUser(auth.getName());
        Task task = taskService.getById(id);

        if(task == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
        if(task.getUserID() != user.getID() && !user.getRole().equals("ROLE_ADMIN"))
            return new ResponseEntity<Task>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks/add", consumes = "application/json")
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = operatorService.getUser(auth.getName());
        if(task.getUserID() == null)
            task.setUserID(user.getID());

        if(task.getUserID() != user.getID() && !user.getRole().equals("ROLE_ADMIN"))
            return new ResponseEntity<Task>(HttpStatus.FORBIDDEN);
        taskService.add(task);
        return new ResponseEntity<Task>(task, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/delete/{id}")
    public ResponseEntity<Task> deleteTaskByID(@PathVariable("id") int id) {
        if(taskService.getById(id) == null)
            return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = operatorService.getUser(auth.getName());
        if(taskService.getById(id).getUserID() != user.getID() && !user.getRole().equals("ROLE_ADMIN"))
            return new ResponseEntity<Task>(HttpStatus.FORBIDDEN);

        taskService.delete(id);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

}*/