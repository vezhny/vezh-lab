package vezh_bank.persistence.dao;

import java.util.List;

public interface GlobalDao<T> extends GlobalSelectDao<T> {
    void deleteAll();
    void delete(T t);
    void update(T t);
    void insert(T t);
    int selectCount();

    @Override
    List<T> selectAll();

    @Override
    T getById(int id);
}
