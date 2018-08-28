package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.UserRequest;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRequestDao extends GlobalDao<UserRequest> {
    @Transactional
    @Override
    List<UserRequest> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(UserRequest userRequest);

    @Transactional
    @Override
    UserRequest getById(int id);

    @Transactional
    @Override
    void update(UserRequest userRequest);

    @Transactional
    @Override
    void insert(UserRequest userRequest);
}
