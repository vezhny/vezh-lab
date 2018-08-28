package vezh_bank.persistence.dao.impl;

import org.apache.log4j.Logger;
import vezh_bank.persistence.dao.CardDao;
import vezh_bank.persistence.entity.Card;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Card card) {
        logger.info("Insert card: " + card);
        entityManager.persist(card);
    }

    @Override
    public List<Card> selectAll() {
        logger.info("Select all cards");
        List<Card> cards;
        try {
            cards = entityManager.createQuery(
                    "SELECT c FROM Card c ORDER BY c.pan", Card.class).getResultList();
        } catch (NoResultException e) {
            cards = new ArrayList<>();
        }
        logger.info("Found " + cards.size() + " of cards");
        return cards;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all cards");
        entityManager.clear();
    }

    @Override
    public void delete(Card card) {
        logger.info("Delete card: " + card);
        entityManager.remove(card);
    }

    @Override
    public Card getById(int id) {
        logger.info("Select card with ID: " + id);
        try {
            Card card = entityManager.createQuery("SELECT c FROM Card c WHERE " +
                    "c.id = :id", Card.class).setParameter("id", id).getSingleResult();
            logger.info(card);
            return card;
        } catch (NoResultException e) {
            logger.info("Card with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public void update(Card card) {
        logger.info("Update card: " + card);
        entityManager.merge(card);
    }
}
