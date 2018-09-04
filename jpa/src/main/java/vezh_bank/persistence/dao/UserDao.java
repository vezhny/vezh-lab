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
    void delete(int id);

    @Transactional
    @Override
    User getById(int id);

    @Transactional
    @Override
    int selectCount();

    @Transactional
    @Override
    void update(User user);

    @Transactional
    @Override
    void insert(User user);

    @Transactional
    List<User> select(String login, String role, String blocked, String data);

    @Transactional
    int selectCount(String login, String role, String blocked, String data);

    @Transactional
    List<User> select(int requiredPage, int rowsOnPage, String login, String role, String blocked, String data);
}
