package todo.dao.impl;

import todo.dao.TaskDao;
import todo.domain.Task;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Олег on 05.03.2017.
 */
@Repository("taskDao")
public class TaskDaoImpl extends HibernateDaoSupport implements TaskDao {
    @Autowired
    public TaskDaoImpl(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public void add(Task task) {
        getHibernateTemplate().save(task);
    }

    public void update(Task task) {
        getHibernateTemplate().update(task);
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTaskList() {
        return (List<Task>)getHibernateTemplate().find("from Task");
    }

    public void delete(Integer id) {
        Task contact = (Task)getHibernateTemplate().load(Task.class, id);
        if(contact != null)
            getHibernateTemplate().delete(contact);
    }

    public Task getById(int id) {
        return getHibernateTemplate().get(Task.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Task> getUserTasks(Integer userID) {
        return (List<Task>)getHibernateTemplate().find("from Task where user.ID = ?", userID);
    }
}
