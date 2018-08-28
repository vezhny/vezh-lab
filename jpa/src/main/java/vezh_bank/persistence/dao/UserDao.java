package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends GlobalDao<User> {
    @Transactional
    @Override
    List<User> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(User user);

    @Transactional
    @Override
    User getById(int id);

    @Transactional
    @Override
    void update(User user);
}
