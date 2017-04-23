package todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import todo.service.UserService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */
public class Task implements Serializable {
    private static Integer lastID=0;

    @Autowired
    UserService userService;

    private Integer id;
    @JsonIgnore
    private User user;
    private Integer userID;
    private String name;
    private String description;
    private Integer priority;
    private Integer status;

    public Task() {
        id = ++lastID;
    }

    public Task(String name, User user, String description, Integer priority, Integer status) {
        id = ++lastID;
        this.name = name;
        this.user = user;
        this.userID = user.getID();
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

   public Task(String name, Integer userID, String description, Integer priority, Integer status) {
        id = ++lastID;
        this.name = name;
        this.user = userService.getById(userID);
        this.userID = userID;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        if(lastID < id)
            lastID = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}
