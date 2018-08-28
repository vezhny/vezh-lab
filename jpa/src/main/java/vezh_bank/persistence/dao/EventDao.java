package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Event;

import javax.transaction.Transactional;
import java.util.List;

public interface EventDao extends GlobalDao<Event> {
    @Transactional
    @Override
    List<Event> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(Event event);

    @Transactional
    @Override
    Event getById(int id);

    @Transactional
    @Override
    void update(Event event);
}
