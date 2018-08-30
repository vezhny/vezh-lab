package vezh_bank.persistence.dao.impl;

import org.apache.log4j.Logger;
import vezh_bank.persistence.dao.UserDao;
import vezh_bank.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> selectAll() {
        logger.info("Select all users");
        List<User> users;
        try {
            users = entityManager.createQuery("SELECT u FROM User u ORDER BY u.login", User.class).getResultList();
        } catch (NoResultException e) {
            users = new ArrayList<>();
        }
        logger.info("Found " + users.size() + " of users");
        return users;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all users");
        entityManager.clear();
    }

    @Override
    public void delete(User user) {
        logger.info("Delete user: " + user);
        entityManager.remove(user);
    }

    @Override
    public User getById(int id) {
        logger.info("Select user with ID: " + id);
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id).getSingleResult();
            logger.info(user);
            return user;
        } catch (NoResultException e) {
            logger.info("User with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public int selectCount() {
        logger.info("Select number of users");
        int numberOfUsers = entityManager.createQuery("SELECT COUNT(*) FROM User u", Long.class).getSingleResult().intValue();
        logger.info("Number of users: " + numberOfUsers);
        return numberOfUsers;
    }

    @Override
    public void update(User user) {
        logger.info("Update user: " + user);
        entityManager.merge(user);
    }

    @Override
    public void insert(User user) {
        logger.info("Insert user: " + user);
        entityManager.persist(user);
    }
}
