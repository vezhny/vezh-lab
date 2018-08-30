package vezh_bank.persistence.dao.impl;

import org.apache.log4j.Logger;
import vezh_bank.persistence.dao.UserRequestDao;
import vezh_bank.persistence.entity.UserRequest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class UserRequestDaoImpl implements UserRequestDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserRequest> selectAll() {
        logger.info("Select all user requests");
        List<UserRequest> userRequests;
        try {
            userRequests = entityManager.createQuery("SELECT ur FROM UserRequest ur ORDER BY ur.date", UserRequest.class)
                    .getResultList();
        } catch (NoResultException e) {
            userRequests = new ArrayList<>();
        }
        logger.info("Found " + userRequests.size() + " of user requests");
        return userRequests;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all user requests");
        entityManager.clear();
    }

    @Override
    public void delete(UserRequest userRequest) {
        logger.info("Delete user request: " + userRequest);
        entityManager.remove(userRequest);
    }

    @Override
    public UserRequest getById(int id) {
        logger.info("Select user request with ID: " + id);
        try {
            UserRequest userRequest = entityManager.createQuery("SELECT ur FROM UserRequest ur WHERE ur.id = :id",
                    UserRequest.class).setParameter("id", id).getSingleResult();
            logger.info(userRequest);
            return userRequest;
        } catch (NoResultException e) {
            logger.info("User request with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public int selectCount() {
        logger.info("Select number of user requests");
        int numberOfRequests = entityManager.createQuery("SELECT COUNT(*) FROM UserRequest ur", Long.class).getSingleResult().intValue();
        logger.info("Number of user requests: " + numberOfRequests);
        return numberOfRequests;
    }

    @Override
    public void update(UserRequest userRequest) {
        logger.info("Update user request: " + userRequest);
        entityManager.merge(userRequest);
    }

    @Override
    public void insert(UserRequest userRequest) {
        logger.info("Insert user request: " + userRequest);
        entityManager.persist(userRequest);
    }
}
