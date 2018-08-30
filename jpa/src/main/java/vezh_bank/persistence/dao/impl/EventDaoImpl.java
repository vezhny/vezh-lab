package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.EventDao;
import vezh_bank.persistence.entity.Event;
import vezh_bank.util.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> selectAll() {
        logger.info("Select all events");
        List<Event> events;
        try {
            events = entityManager.createQuery("SELECT e FROM Event e ORDER BY e.date", Event.class).getResultList();
        } catch (NoResultException e) {
            events = new ArrayList<>();
        }
        logger.info("Found " + events.size() + " of events");
        return events;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all events");
        entityManager.clear();
    }

    @Override
    public void delete(Event event) {
        logger.info("Delete event: " + event);
        entityManager.remove(event);
    }

    @Override
    public Event getById(int id) {
        logger.info("Select event with ID: " + id);
        try {
            Event event = entityManager.createQuery("SELECT e FROM Event e WHERE e.id = :id", Event.class)
                    .setParameter("id", id).getSingleResult();
            logger.info(event);
            return event;
        } catch (NoResultException e) {
            logger.info("Event with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public int selectCount() {
        logger.info("Select number of events");
        int numberOfEvents = entityManager.createQuery("SELECT COUNT(*) FROM Event e", Long.class).getSingleResult().intValue();
        logger.info("Number of events: " + numberOfEvents);
        return numberOfEvents;
    }

    @Override
    public void update(Event event) {
        logger.info("Update event: " + event);
        entityManager.merge(event);
    }

    @Override
    public void insert(Event event) {
        logger.info("Insert event: " + event);
        entityManager.persist(event);
    }
}
