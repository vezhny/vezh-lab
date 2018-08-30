package vezh_bank.persistence.dao;

import java.util.List;

public interface GlobalSelectDao<T> {
    List<T> selectAll();
    T getById(int id);

    default String getLikeParam(String field) {
        return "%" + field  + "%";
    }

    default int getFirstResultIndex(int requiredPage, int rowsOnPage) {
        return (requiredPage - 1) * rowsOnPage;
    }
}
