package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        String query = "CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL , " +
                "lastname VARCHAR(45) NOT NULL , " +
                "age TINYINT NOT NULL, " +
                "PRIMARY KEY (id))";
        try {
            session.createSQLQuery(query).executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.createSQLQuery("DROP TABLE users").executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        User user = new User(name, lastName, age);

        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.createQuery(String.format("delete from User where id = %d", id)).executeUpdate();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> result = session.createCriteria(User.class).list();
        session.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.createQuery("delete from User").executeUpdate();
        session.close();
    }
}
