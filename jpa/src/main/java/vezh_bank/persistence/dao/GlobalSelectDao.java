package vezh_bank.persistence.dao;

import java.util.List;

public interface GlobalSelectDao<T> {
    List<T> selectAll();
    T getById(int id);
}
