package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.UserDao;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Logger;

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
        entityManager.createNativeQuery("DELETE FROM USERS").executeUpdate();
    }

    @Override
    public void delete(User user) {
        delete(user.getId());
    }

    @Override
    public void delete(int id) {
        logger.info("Delete user with ID: " + id);
        entityManager.createNativeQuery("DELETE FROM USERS WHERE USER_ID = ?")
                .setParameter(1, id)
                .executeUpdate();
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

    @Override
    public List<User> select(String login, String role, String blocked, String data) {
        logger.info("Select users");
        logger.info("Login: " + login);
        logger.info("Role: " + role);
        logger.info("Blocked: " + blocked);
        logger.info("Data: " + data);
        List<User> users;
        try {
            users = entityManager.createQuery("SELECT u FROM User u WHERE " +
                    "u.login LIKE :login AND u.role.name LIKE :role " +
                    "AND CAST(u.blocked AS string) LIKE :blocked AND u.data LIKE :data ORDER BY u.login", User.class)
                    .setParameter("login", getLikeParam(login))
                    .setParameter("role", getLikeParam(role))
                    .setParameter("blocked", getLikeParam(blocked))
                    .setParameter("data", getLikeParam(data))
                    .getResultList();
        } catch (NoResultException e) {
            users = new ArrayList<>();
        }
        logger.info("Found " + users.size() + " of users");
        return users;
    }

    @Override
    public int selectCount(String login, String role, String blocked, String data) {
        logger.info("Select number of users");
        logger.info("Login: " + login);
        logger.info("Role: " + role);
        logger.info("Blocked: " + blocked);
        logger.info("Data: " + data);
        int users = 0;
        try {
            users = entityManager.createQuery("SELECT COUNT(*) FROM User u WHERE " +
                    "u.login LIKE :login AND u.role.name LIKE :role " +
                    "AND CAST(u.blocked AS string) LIKE :blocked AND u.data LIKE :data", Long.class)
                    .setParameter("login", getLikeParam(login))
                    .setParameter("role", getLikeParam(role))
                    .setParameter("blocked", getLikeParam(blocked))
                    .setParameter("data", getLikeParam(data))
                    .getSingleResult().intValue();
        } catch (NoResultException e) {
        }
        logger.info("Found " + users + " of users");
        return users;
    }

    @Override
    public List<User> select(int requiredPage, int rowsOnPage, String login, String role, String blocked, String data) {
        logger.info("Select users");
        logger.info("Login: " + login);
        logger.info("Role: " + role);
        logger.info("Blocked: " + blocked);
        logger.info("Data: " + data);
        List<User> users;
        try {
            users = entityManager.createQuery("SELECT u FROM User u WHERE " +
                    "u.login LIKE :login AND u.role.name LIKE :role " +
                    "AND CAST(u.blocked AS string) LIKE :blocked AND u.data LIKE :data ORDER BY u.login", User.class)
                    .setParameter("login", getLikeParam(login))
                    .setParameter("role", getLikeParam(role))
                    .setParameter("blocked", getLikeParam(blocked))
                    .setParameter("data", getLikeParam(data))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage)
                    .getResultList();
        } catch (NoResultException e) {
            users = new ArrayList<>();
        }
        logger.info("Found " + users.size() + " of users");
        return users;
    }

    @Override
    public boolean isLoginUnique(String login) {
        logger.info("Checking if login " + login + " unique");
        if (select(login) == null) {
            return true;
        }
        return false;
    }

    @Override
    public User select(String login) { // TODO: test
        logger.info("Select user with login: " + login);
        User user;
        try {
            user = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            logger.info("Found user with login \"" + login + "\"");
        } catch (NoResultException e) {
            logger.info("User with login \"" + login + "\" not found");
            user = null;
        }
        return user;
    }
}
