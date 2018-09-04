package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.UserRequestDao;
import vezh_bank.persistence.entity.UserRequest;
import vezh_bank.util.Logger;

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
            userRequests = entityManager.createQuery("SELECT ur FROM UserRequest ur ORDER BY ur.date DESC ", UserRequest.class)
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
        entityManager.createNativeQuery("DELETE FROM USER_REQUESTS").executeUpdate();
    }

    @Override
    public void delete(UserRequest userRequest) {
        delete(userRequest.getId());
    }

    @Override
    public void delete(int requestId) {
        logger.info("Delete user request wit ID: " + requestId);
        entityManager.createNativeQuery("DELETE FROM USER_REQUESTS WHERE REQUEST_ID = ?")
                .setParameter(1, requestId)
                .executeUpdate();
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

    @Override
    public List<UserRequest> select(String userId, String creationDate, String status, String data) {
        logger.info("Select user requests");
        logger.info("User ID: " + userId);
        logger.info("Creation date: " + creationDate);
        logger.info("Status: " + status);
        logger.info("Data: " + data);
        List<UserRequest> userRequests;
        try {
            userRequests = entityManager.createQuery("SELECT ur FROM UserRequest ur WHERE CAST(ur.user.id AS string) " +
                    "LIKE :userId AND ur.date LIKE :date AND ur.status LIKE :status AND ur.data LIKE :data " +
                    "ORDER BY ur.date DESC ", UserRequest.class)
                    .setParameter("userId", getLikeParam(userId))
                    .setParameter("date", getLikeParam(creationDate))
                    .setParameter("status", getLikeParam(status))
                    .setParameter("data", getLikeParam(data))
                    .getResultList();
        } catch (NoResultException e) {
            userRequests = new ArrayList<>();
        }
        logger.info("Found " + userRequests.size() + " of user requests");
        return userRequests;
    }

    @Override
    public int selectCount(String userId, String creationDate, String status, String data) {
        logger.info("Select number of user requests");
        logger.info("User ID: " + userId);
        logger.info("Creation date: " + creationDate);
        logger.info("Status: " + status);
        logger.info("Data: " + data);
        int userRequests = 0;
        try {
            userRequests = entityManager.createQuery("SELECT COUNT(*) FROM UserRequest ur WHERE CAST(ur.user.id AS string) " +
                    "LIKE :userId AND ur.date LIKE :date AND ur.status LIKE :status AND ur.data LIKE :data ", Long.class)
                    .setParameter("userId", getLikeParam(userId))
                    .setParameter("date", getLikeParam(creationDate))
                    .setParameter("status", getLikeParam(status))
                    .setParameter("data", getLikeParam(data))
                    .getSingleResult().intValue();
        } catch (NoResultException e) {
        }
        logger.info("Found " + userRequests + " of user requests");
        return userRequests;
    }

    @Override
    public List<UserRequest> select(int requiredPage, int rowsOnPage, String userId, String creationDate, String status, String data) {
        logger.info("Select user requests");
        logger.info("Required page: " + requiredPage);
        logger.info("Rows on page: " + rowsOnPage);
        logger.info("User ID: " + userId);
        logger.info("Creation date: " + creationDate);
        logger.info("Status: " + status);
        logger.info("Data: " + data);
        List<UserRequest> userRequests;
        try {
            userRequests = entityManager.createQuery("SELECT ur FROM UserRequest ur WHERE CAST(ur.user.id AS string) " +
                    "LIKE :userId AND ur.date LIKE :date AND ur.status LIKE :status AND ur.data LIKE :data " +
                    "ORDER BY ur.date DESC ", UserRequest.class)
                    .setParameter("userId", getLikeParam(userId))
                    .setParameter("date", getLikeParam(creationDate))
                    .setParameter("status", getLikeParam(status))
                    .setParameter("data", getLikeParam(data))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage)
                    .getResultList();
        } catch (NoResultException e) {
            userRequests = new ArrayList<>();
        }
        logger.info("Found " + userRequests.size() + " of user requests");
        return userRequests;
    }

    @Override
    public List<UserRequest> select(int userId) {
        logger.info("Select user requests with user ID: " + userId);
        List<UserRequest> userRequests;
        try {
            userRequests = entityManager.createQuery("SELECT ur FROM UserRequest ur WHERE " +
                    "ur.user.id = :userId ORDER BY ur.date DESC ", UserRequest.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            userRequests = new ArrayList<>();
        }
        logger.info("Found " + userRequests.size() + " of user requests");
        return userRequests;
    }
}
