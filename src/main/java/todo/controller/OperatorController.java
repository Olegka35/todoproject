package todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import todo.domain.Article;
import todo.domain.Attribute;
import todo.domain.Operator;
import todo.service.ArticleService;
import todo.service.OperatorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 08.06.2018.
 */

@Controller
public class OperatorController {
    private final OperatorService operatorService;
    private final ArticleService articleService;

    @Autowired
    public OperatorController(OperatorService operatorService, ArticleService articleService) {
        this.operatorService = operatorService;
        this.articleService = articleService;
    }

    @ModelAttribute("operatorBean")
    public Operator createOperatorBean() {
        return new Operator();
    }

    @ModelAttribute("articleBean")
    public Article createArticleBean() {
        return new Article();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public ModelAndView regPage() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ModelAndView regUser(@RequestParam("login") String login,
                                @RequestParam("password") String password) {
        if (operatorService.getUser(login) != null) {
            return new ModelAndView("regerror");
        }
        Operator user = new Operator(-1, login, password, "ROLE_USER");
        operatorService.add(user);
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/params", method = RequestMethod.GET)
    public ModelAndView operatorPage() {
        List<Map<String, Object>> params = operatorService.getArticleParams();
        return new ModelAndView("params", "params", params);
    }

    @RequestMapping(value = "params/addparam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addParam(@RequestBody Attribute attr) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (operatorService.getAttrIDByName(attr.getName()) != null) {
            responseMap.put("error", true);
            responseMap.put("text", "Attribute is already exist");
        }
        else {
            operatorService.addArticleParam(attr.getName());
            responseMap.put("error", false);
        }
        return responseMap;
    }

    @RequestMapping(value = "params/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteParam(@RequestBody Attribute attr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Operator user = operatorService.getUser(auth.getName());
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if(user == null || !user.getRole().equals("ROLE_ADMIN")) {
            responseMap.put("text", "Forbidden");
            responseMap.put("error", true);
        }
        else {
            if (operatorService.deleteArticleParam(attr.getId())) {
                responseMap.put("error", false);
            }
            else {
                responseMap.put("text", "This attribute cannot be deleted");
                responseMap.put("error", true);
            }
        }
        return responseMap;
    }
    /*@RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView displayUsers() {
        UserList userList = new UserList();
        userList.setUserList(userService.getAllUsers(1));
        userList.setPage(1);
        userList.setPageNumEx(userService.getAllUsers().size());

        return new ModelAndView("admin", "userList", userList);
    }

    @RequestMapping(value = "/admin/search_task", method = RequestMethod.POST)
    public
    @ResponseBody
    TaskList searchTasks(@RequestBody SearchText field) {
        String search = field.getText();
        TaskList taskList = new TaskList();
        taskList.setArticleList(taskService.getArticleList(field.getPage(), search));
        taskList.setPage(field.getPage());
        taskList.setPageNumEx(taskService.getArticleList(search).size());
        taskList.setSearch(search);
        return taskList;
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public @ResponseBody UserList deleteUser(@RequestBody UserQuery userQuery) {
        if(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getID() != userQuery.getUserID())
            userService.delete(userQuery.getUserID());

        UserList userList = new UserList();
        userList.setUserList(userService.getAllUsers(userQuery.getPage()));
        userList.setPage(userQuery.getPage());
        userList.setPageNumEx(userService.getAllUsers().size());
        return userList;
    }

    @RequestMapping(value = "/admin/role", method = RequestMethod.POST)
    public @ResponseBody UserList changeUserRole(@RequestBody UserQuery userQuery) {
        User user = userService.getById(userQuery.getUserID());
        if (user != null && userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getID() != userQuery.getUserID()) {
            if(user.getRole().equals("ROLE_ADMIN"))
                user.setRole("ROLE_USER");
            else
                user.setRole("ROLE_ADMIN");
            userService.update(user);
        }

        UserList userList = new UserList();
        userList.setUserList(userService.getAllUsers(userQuery.getPage()));
        userList.setPage(userQuery.getPage());
        userList.setPageNumEx(userService.getAllUsers().size());
        return userList;
    }

    @RequestMapping(value = "/admin/page", method = RequestMethod.POST)
    public
    @ResponseBody
    UserList userPage(@RequestBody SearchText field) {
        Integer page = field.getPage();
        UserList userList = new UserList();
        userList.setUserList(userService.getAllUsers(page));
        userList.setPage(page);
        userList.setPageNumEx(userService.getAllUsers().size());
        return userList;
    }*/
}
