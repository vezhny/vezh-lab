package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Event;

import javax.transaction.Transactional;
import java.util.List;

public interface EventDao extends GlobalSelectDao<Event> {
    @Transactional
    @Override
    List<Event> selectAll();

    @Transactional
    void deleteAll();

    @Transactional
    @Override
    Event getById(int id);

    @Transactional
    List<Event> select(String type, String date, String data);

    @Transactional
    int selectCount();

    @Transactional
    void insert(Event event);

    @Transactional
    List<Event> select(int requiredPage, int rowsOnPage,
                       String type, String date, String data);
}
