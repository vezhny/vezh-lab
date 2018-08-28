package vezh_bank.persistence.dao.impl;

import org.apache.log4j.Logger;
import vezh_bank.persistence.dao.CurrencyDao;
import vezh_bank.persistence.entity.Currency;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Currency currency) {
        logger.info("Insert currency: " + currency);
        entityManager.persist(currency);
    }

    @Override
    public List<Currency> selectAll() {
        logger.info("Select all currencies");
        List<Currency> currencies;
        try {
            currencies = entityManager.createQuery("SELECT c FROM Currency c " +
                    "ORDER BY c.value", Currency.class).getResultList();
        } catch (NoResultException e) {
            currencies = new ArrayList<>();
        }
        logger.info("Found " + currencies.size() + " of currencies");
        return currencies;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all currencies");
        entityManager.clear();
    }

    @Override
    public void delete(Currency currency) {
        logger.info("Delete currency: " + currency);
        entityManager.remove(currency);
    }

    @Override
    public Currency getById(int id) {
        logger.info("Select currency with code: " + id);
        try {
            Currency currency = entityManager.createQuery("SELECT c FROM Currency c " +
                    "WHERE c.code = :id", Currency.class)
                    .setParameter("id", id).getSingleResult();
            logger.info(currency);
            return currency;
        } catch (NoResultException e) {
            logger.info("Currency with code " + id + "not found");
            return null;
        }
    }

    @Override
    public void update(Currency currency) {
        logger.info("Update currency: " + currency);
        entityManager.merge(currency);
    }
}
