package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.UserRole;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleDao extends GlobalDao<UserRole> {
    @Transactional
    @Override
    List<UserRole> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(UserRole userRole);

    @Transactional
    @Override
    UserRole getById(int id);

    @Transactional
    @Override
    void update(UserRole userRole);
}
