package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.UserRole;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleDao extends GlobalSelectDao<UserRole> {
    @Transactional
    @Override
    List<UserRole> selectAll();

    @Transactional
    @Override
    UserRole getById(int id);
}
