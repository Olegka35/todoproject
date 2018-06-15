package todo.service.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import todo.configuration.SpringJDBCConfiguration;
import todo.configuration.ToDoListConfiguration;
import todo.dao.TaskDao;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 05.03.2017.
 */
/*@Service("taskService")
public class TaskServiceImpl implements TaskService {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfiguration.class);
    TaskDao taskDAO = context.getBean(TaskDao.class);


    public void add(Task task) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", task.getName());
        params.put("description", task.getDescription());
        params.put("user", task.getUserID().toString());
        params.put("priority", task.getPriority().toString());
        params.put("status", task.getStatus().toString());
        params.put("due_date", new SimpleDateFormat("yyyy.MM.dd").format(task.getDueDate()));
        taskDAO.add("task", params, "name");
    }

    public void update(Task task) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", task.getName());
        params.put("description", task.getDescription());
        params.put("user", task.getUserID().toString());
        params.put("priority", task.getPriority().toString());
        params.put("status", task.getStatus().toString());
        params.put("due_date", new SimpleDateFormat("yyyy.MM.dd").format(task.getDueDate()));
        taskDAO.update(task.getId(), params);
    }

    public List<Task> getArticleList() {
        List<Map<String, Object>> tasks = taskDAO.getList("task");
        List<Task> resultList = new ArrayList<Task>();
        for(Map<String, Object> map: tasks) {
            resultList.add(convertMapToTask(map));
        }
        return resultList;
    }

    public List<Task> getArticleList(Integer page) {
        List<Map<String, Object>> tasks = taskDAO.getList("task", (page-1)*ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE);
        List<Task> resultList = new ArrayList<Task>();
        for(Map<String, Object> map: tasks) {
            resultList.add(convertMapToTask(map));
        }
        return resultList;
    }

    public List<Task> getArticleList(String search) {
        List<Map<String, Object>> tasks = taskDAO.getList("task", getFilterMap(null, null, search));
        List<Task> resultList = new ArrayList<Task>();
        for(Map<String, Object> map: tasks) {
            resultList.add(convertMapToTask(map));
        }
        return resultList;
    }

    public List<Task> getArticleList(Integer page, String search) {
        List<Map<String, Object>> tasks = taskDAO.getList("task", (page-1)*ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE, getFilterMap(null, null, search));
        List<Task> resultList = new ArrayList<Task>();
        for(Map<String, Object> map: tasks) {
            resultList.add(convertMapToTask(map));
        }
        return resultList;
    }

    public Task getById(int id) {
        Map<String, Object> map = taskDAO.getById(id);
        if(map == null) return null;

        return convertMapToTask(map);
    }

    public void delete(Integer id) {
        taskDAO.delete(id);
    }

    public List<Task> getUserTasks(Integer userID) {
        List<Integer> list = taskDAO.getIDsByParam("task", "user", userID.toString());
        List<Task> taskList = new ArrayList<Task>();
        for(Integer id: list) {
            taskList.add(getById(id));
        }
        return taskList;
    }

    public List<Task> getUserTasks(Integer userID, int page) {
        List<Integer> list = taskDAO.getIDsByParam("task", "user", userID.toString(), (page-1)*ToDoListConfiguration.TASKS_ON_PAGE, ToDoListConfiguration.TASKS_ON_PAGE);
        List<Task> taskList = new ArrayList<Task>();
        for(Integer id: list) {
            taskList.add(getById(id));
        }
        return taskList;
    }

    public List<Task> getUserTasks(Integer userID, int page, String order_field, Boolean reversed, Integer filterStatus, Integer filterPriority, String search) {
        Map<String, String> filterMap = getFilterMap(filterStatus, filterPriority, search);

        List<Integer> list = taskDAO.getIDsByParam("task",
                "user",
                userID.toString(),
                (page-1)*ToDoListConfiguration.TASKS_ON_PAGE,
                ToDoListConfiguration.TASKS_ON_PAGE,
                order_field,
                reversed,
                filterMap);
        List<Task> taskList = new ArrayList<Task>();
        for(Integer id: list) {
            taskList.add(getById(id));
        }
        return taskList;
    }

    public Integer getUserTasksNum(Integer userID) {
        List<Integer> list = taskDAO.getIDsByParam("task", "user", userID.toString());
        return list.size();
    }

    public Integer getUserTasksNum(Integer userID, Integer filterStatus, Integer filterPriority, String search) {
        Map<String, String> filterMap = getFilterMap(filterStatus, filterPriority, search);
        List<Integer> list = taskDAO.getIDsByParam("task", "user", userID.toString(), filterMap);
        return list.size();
    }

    private Date getDateFromMap(Map<String, Object> map, String param)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date dueDate = null;
        try {
            dueDate = format.parse(map.get(param).toString());
        } catch (ParseException | NullPointerException e) {
            dueDate = new Date(0);
        }
        return dueDate;
    }

    private Task convertMapToTask(Map<String, Object> map)
    {
        return new Task(Integer.parseInt(map.get("ID").toString()), map.get("name").toString(),
                Integer.parseInt(map.get("user").toString()), map.get("description").toString(),
                Integer.parseInt(map.get("priority").toString()), Integer.parseInt(map.get("status").toString()), getDateFromMap(map, "due_date"));
    }


    private Map<String, String> getFilterMap(Integer filterStatus, Integer filterPriority, String search)
    {
        Map<String, String> filterMap = new HashMap<String, String>();
        if(filterPriority != null && filterPriority != 0) {
            filterMap.put("priority", filterPriority.toString());
        }
        if(filterStatus != null && filterStatus != 0) {
            filterMap.put("status", filterStatus.toString());
        }
        if(search != null && search.length() > 0) {
            filterMap.put("name description", search);
        }
        if(filterMap.isEmpty()) filterMap = null;
        return filterMap;
    }
}
*/