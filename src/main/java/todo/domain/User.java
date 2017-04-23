package todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Олег on 29.03.2017.
 */
public class User {
    private int ID;
    private String Login;
    private String Password;
    @JsonIgnore
    private Set<Task> tasks = new HashSet<Task>(0);
    private String Role;

    public User() {
    }

    public User(String login, String password, String role) {
        Login = login;
        Password = password;
        Role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public int getTasksNum() {
        return tasks.size();
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
