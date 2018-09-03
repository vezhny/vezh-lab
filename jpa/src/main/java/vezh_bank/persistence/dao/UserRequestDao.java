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
    void delete(int requestId);

    @Transactional
    @Override
    UserRequest getById(int id);

    @Transactional
    @Override
    int selectCount();

    @Transactional
    @Override
    void update(UserRequest userRequest);

    @Transactional
    @Override
    void insert(UserRequest userRequest);

    @Transactional
    List<UserRequest> select(String userId, String creationDate, String status, String data);

    @Transactional
    int selectCount(String userId, String creationDate, String status, String data);

    @Transactional
    List<UserRequest> select(int requiredPage, int rowsOnPage, String userId, String creationDate,
                             String status, String data);

    @Transactional
    List<UserRequest> select(int userId);
}
