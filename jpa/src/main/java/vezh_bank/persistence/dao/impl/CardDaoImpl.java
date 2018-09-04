package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.CardDao;
import vezh_bank.persistence.entity.Card;
import vezh_bank.util.Logger;

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
    public int selectCount() {
        logger.info("Select number of cards");
        int numberOfCards = entityManager.createQuery("SELECT COUNT(*) FROM Card c",
                Long.class).getSingleResult().intValue();
        logger.info("Number of cards: " + numberOfCards);
        return numberOfCards;
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
        entityManager.createNativeQuery("DELETE FROM CARDS").executeUpdate();
    }

    @Override
    public void delete(Card card) {
        delete(card.getId());
    }

    @Override
    public void delete(int cardId) {
        logger.info("Delete card with ID: " + cardId);
        entityManager.createNativeQuery("DELETE FROM CARDS WHERE CARD_ID = ?")
                .setParameter(1, cardId)
                .executeUpdate();
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

    @Override
    public List<Card> select(int holderId) {
        logger.info("Select cards where holder ID: " + holderId);
        List<Card> cards;
        try {
            cards = entityManager.createQuery("SELECT c FROM Card c WHERE " +
                    "c.holder.id = :holderId ORDER BY c.pan", Card.class)
                    .setParameter("holderId", holderId)
                    .getResultList();
        } catch (NoResultException e) {
            cards = new ArrayList<>();
        }
        logger.info("Found " + cards.size() + " of cards");
        return cards;
    }

    @Override
    public List<Card> select(String pan, String holderName, String creationDate,
                             String expiry, String currency, String status) {
        logger.info("Select cards");
        logger.info("PAN: " + pan);
        logger.info("Holder name: " + holderName);
        logger.info("Creation date: " + creationDate);
        logger.info("Expiry: " + expiry);
        logger.info("Currency: " + currency);
        logger.info("Status: " + status);
        List<Card> cards;
        try {
            cards = entityManager.createQuery("SELECT c FROM Card c WHERE " +
                    "c.pan LIKE :pan AND " +
                    "c.holder.data LIKE :holderName AND " +
                    "c.creationDate LIKE :creationDate AND " +
                    "c.expiry LIKE :expiry AND " +
                    "(CAST(c.currency.code AS string) LIKE :currency OR c.currency.value LIKE :currency) AND " +
                    "c.status LIKE :status ORDER BY c.pan", Card.class)
                    .setParameter("pan", getLikeParam(pan))
                    .setParameter("holderName", getLikeParam(holderName))
                    .setParameter("creationDate", getLikeParam(creationDate))
                    .setParameter("expiry", getLikeParam(expiry))
                    .setParameter("currency", getLikeParam(currency))
                    .setParameter("status", getLikeParam(status))
                    .getResultList();
        } catch (NoResultException e) {
            cards = new ArrayList<>();
        }
        logger.info("Found " + cards.size() + " of cards");
        return cards;
    }

    @Override
    public int selectCount(String pan, String holderName, String creationDate, String expiry,
                           String currency, String status) {
        logger.info("Select number of cards");
        logger.info("PAN: " + pan);
        logger.info("Holder name: " + holderName);
        logger.info("Creation date: " + creationDate);
        logger.info("Expiry: " + expiry);
        logger.info("Currency: " + currency);
        logger.info("Status: " + status);
        int cards = 0;
        try {
            cards = entityManager.createQuery("SELECT COUNT(*) FROM Card c WHERE " +
                    "c.pan LIKE :pan AND " +
                    "c.holder.data LIKE :holderName AND " +
                    "c.creationDate LIKE :creationDate AND " +
                    "c.expiry LIKE :expiry AND " +
                    "(CAST(c.currency.code AS string) LIKE :currency OR c.currency.value LIKE :currency) AND " +
                    "c.status LIKE :status ", Long.class)
                    .setParameter("pan", getLikeParam(pan))
                    .setParameter("holderName", getLikeParam(holderName))
                    .setParameter("creationDate", getLikeParam(creationDate))
                    .setParameter("expiry", getLikeParam(expiry))
                    .setParameter("currency", getLikeParam(currency))
                    .setParameter("status", getLikeParam(status))
                    .getSingleResult().intValue();
        } catch (NoResultException e) {
        }
        logger.info("Found " + cards + " of cards");
        return cards;
    }

    @Override
    public List<Card> select(int requiredPage, int rowsOnPage, String pan, String holderName,
                             String creationDate, String expiry, String currency, String status) {
        logger.info("Select cards");
        logger.info("Required page: " + requiredPage);
        logger.info("Rows on page: " + rowsOnPage);
        logger.info("PAN: " + pan);
        logger.info("Holder name: " + holderName);
        logger.info("Creation date: " + creationDate);
        logger.info("Expiry: " + expiry);
        logger.info("Currency: " + currency);
        logger.info("Status: " + status);
        List<Card> cards;
        try {
            cards = entityManager.createQuery("SELECT c FROM Card c WHERE " +
                    "c.pan LIKE :pan AND " +
                    "c.holder.data LIKE :holderName AND " +
                    "c.creationDate LIKE :creationDate AND " +
                    "c.expiry LIKE :expiry AND " +
                    "(CAST(c.currency.code AS string) LIKE :currency OR c.currency.value LIKE :currency) AND " +
                    "c.status LIKE :status ORDER BY c.pan", Card.class)
                    .setParameter("pan", getLikeParam(pan))
                    .setParameter("holderName", getLikeParam(holderName))
                    .setParameter("creationDate", getLikeParam(creationDate))
                    .setParameter("expiry", getLikeParam(expiry))
                    .setParameter("currency", getLikeParam(currency))
                    .setParameter("status", getLikeParam(status))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage)
                    .getResultList();
        } catch (NoResultException e) {
            cards = new ArrayList<>();
        }
        logger.info("Found " + cards.size() + " of cards");
        return cards;
    }
}
