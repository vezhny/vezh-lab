package vezh_bank.persistence.dao;

import java.util.List;

public interface GlobalDao<T> {
    List<T> selectAll();
    void deleteAll();
    void delete(T t);
    T getById(int id);
    void update(T t);
}
