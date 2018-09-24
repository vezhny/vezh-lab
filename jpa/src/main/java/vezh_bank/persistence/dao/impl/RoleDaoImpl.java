package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.RoleDao;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserRole> selectAll() {
        logger.info("Select all roles");
        List<UserRole> userRoles;
        try {
            userRoles = entityManager.createQuery("SELECT ur FROM UserRole ur", UserRole.class).getResultList();
        } catch (NoResultException e) {
            userRoles = new ArrayList<>();
        }
        logger.info("Found " + userRoles.size() + " of roles");
        return userRoles;
    }

    @Override
    public UserRole getById(int id) {
        logger.info("Select role with ID: " + id);
        try {
            UserRole userRole = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id = :id", UserRole.class)
                    .setParameter("id", id).getSingleResult();
            logger.info(userRole);
            return userRole;
        } catch (NoResultException e) {
            logger.info("Role with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public UserRole get(String name) {
        logger.info("Select role with name \"" + name + "\"");
        try {
            UserRole userRole = entityManager.createQuery("SELECT r FROM UserRole r WHERE r.name = :name",
                    UserRole.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return userRole;
        } catch (NoResultException e) {
            logger.info("Role with name \"" + name + "\" not found");
            return null; //TODO: test fot this
        }//TODO: log params in ""
    }
}
