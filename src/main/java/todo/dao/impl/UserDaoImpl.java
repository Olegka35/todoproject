package todo.dao.impl;

import todo.dao.UserDao;
import todo.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Олег on 30.03.2017.
 */

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public void add(User user) {
        getHibernateTemplate().save(user);
    }

    public void update(User user) {
        getHibernateTemplate().update(user);
    }

    public void delete(Integer id) {
        User user = (User)getHibernateTemplate().load(User.class, id);
        if(user != null)
            getHibernateTemplate().delete(user);
    }

    public User getById(int id) {
        return getHibernateTemplate().load(User.class, id);
    }

    public User getUser(String login) {
        List<User> userList = (List<User>)getHibernateTemplate().find("from User where Login = ?", login);
        if(userList == null || userList.size() == 0) return null;
        return userList.get(0);
    }

    public List<User> getAllUsers() {
        return (List<User>)getHibernateTemplate().find("from User");
    }
}
