package todo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import todo.domain.Task;
import todo.domain.TaskList;
import todo.domain.User;
import todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import todo.service.UserService;

import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @ModelAttribute("taskBean")
    public Task createTaskBean() {
        return new Task();
    }

    @ModelAttribute("userBean")
    public User createUserBean() {
        return new User();
    }

    @RequestMapping(value = "/todolist", method = RequestMethod.GET)
    public ModelAndView displayTasks()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());
        /*if(auth.isAuthenticated()) {
            System.out.println("Вы авторизованы. user = " + auth.getName() + auth.getAuthorities());
        }
        else {
            System.out.println("Вы не авторизованы. user = " + auth.getName() + auth.getAuthorities());
        }*/


        TaskList taskList = new TaskList();
        //List<Task> todoList = taskService.getTaskList();
        List<Task> todoList = taskService.getUserTasks(user.getID());
        taskList.setTaskList(todoList);
        taskList.setLogin(user.getLogin());
        return new ModelAndView("todolist", "taskList", taskList);
    }

    @RequestMapping(value = "/todolist/{id}", method = RequestMethod.GET)
    public ModelAndView displayUserTasks(@PathVariable int id)
    {
        List<User> userList = userService.getAllUsers();
        User reqUser = null;
        for(User user: userList)
            if(user.getID() == id) {
                reqUser = user;
                break;
            }

        TaskList taskList = new TaskList();
            List<Task> todoList = taskService.getUserTasks(id);
            taskList.setTaskList(todoList);

            if(reqUser != null) {
                System.out.println(reqUser.getID());
            taskList.setLogin(reqUser.getLogin());
        }
        return new ModelAndView("todolist", "taskList", taskList);
    }

    @RequestMapping(value = "/todolist/delete/{id}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable int id) {
        taskService.delete(id);
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/todolist/edit", method = RequestMethod.POST)
    public String update(@RequestParam("ID") int id,
                         @RequestParam("name") String name,
                         @RequestParam("descr") String description,
                         @RequestParam("priority") Integer priority,
                         @RequestParam("status") Integer status) {
        Task task = taskService.getById(id);
        task.setName(name);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(status);
        taskService.update(task);
        return "redirect:/todolist/";
    }

    @RequestMapping(value = "/todolist/add", method = RequestMethod.POST)
    public String update(@RequestParam("addname") String name,
                         @RequestParam("adddescr") String description,
                         @RequestParam("addpriority") String priority,
                         @RequestParam("addstatus") String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());

        Task task = new Task(name, user, description, Integer.parseInt(priority), Integer.parseInt(status));
        taskService.add(task);
        return "redirect:/todolist/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage()
    {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public ModelAndView regPage()
    {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ModelAndView regUser(@RequestParam("login") String login,
                                @RequestParam("password") String password)
    {
        if(userService.getUser(login) != null) {
            return new ModelAndView("regerror");
        }
        User user = new User(login, password, "ROLE_USER");
        userService.add(user);
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView displayUsers()
    {
        List<User> userList = userService.getAllUsers();
        return new ModelAndView("admin", "userList", userList);
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id) {
        User user = userService.getById(id);
        for(Task task: user.getTasks()) {
            taskService.delete(task.getId());
        }
        userService.delete(id);
        return "redirect:/admin";
    }
}
