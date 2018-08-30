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
        entityManager.createNativeQuery("DELETE FROM CURRENCIES").executeUpdate();
    }

    @Override
    public void delete(Currency currency) {
        logger.info("Delete currency: " + currency);
        delete(currency.getCode());
    }

    @Override
    public void delete(int code) {
        logger.info("Delete currency with code: " + code);
        entityManager.createNativeQuery("DELETE FROM CURRENCIES WHERE CODE = ?")
                .setParameter(1, code).executeUpdate();
    }

    @Override
    public void delete(String value) {
        logger.info("Delete currency with value: " + value);
        entityManager.createNativeQuery("DELETE FROM CURRENCIES WHERE CURRENCY_VALUE = ?")
                .setParameter(1, value).executeUpdate();
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
    public int selectCount() {
        logger.info("Select number of currencies");
        int numberOFCurrencies = entityManager.createQuery("SELECT COUNT(*) FROM Currency c", Long.class).getSingleResult().intValue();
        logger.info("Number of currencies: " + numberOFCurrencies);
        return numberOFCurrencies;
    }

    @Override
    public List<Currency> getByValue(String value) {
        logger.info("Select currency with value: " + value);
        List<Currency> currencies;
        try {
            currencies = entityManager.createQuery("SELECT c FROM Currency c " +
                    "WHERE c.value LIKE :currencyValue ORDER BY c.value", Currency.class)
                    .setParameter("currencyValue", getLikeParam(value)).getResultList();
            logger.info("Found " + currencies.size() + " of currencies");
            return currencies;
        } catch (NoResultException e) {
            logger.info("Currency with value " + value + " not found");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Currency> get(String code, String value) {
        logger.info("Select currency");
        logger.info("Code: " + code);
        logger.info("Value: " + value);
        List<Currency> currencies;
        try {
            currencies = entityManager.createQuery("SELECT c FROM Currency c WHERE CAST(c.code AS string) LIKE :code " +
                    "AND c.value LIKE :currencyValue ORDER BY c.value", Currency.class)
                    .setParameter("code", getLikeParam(code)).setParameter("currencyValue", getLikeParam(value)).getResultList();
            logger.info("Found " + currencies.size() + " of currencies");
            return currencies;
        } catch (NoResultException e) {
            logger.info("Required currencies not found");
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Currency currency) {
        logger.info("Update currency: " + currency);
        entityManager.merge(currency);
    }

    @Override
    public List<Currency> select(int requiredPage, int rowsOnPage, String code, String value) {
        logger.info("Select currencies");
        logger.info("Required page: " + requiredPage);
        logger.info("Row on page: " + rowsOnPage);
        logger.info("Code: " + code);
        logger.info("Value: " + value);
        List<Currency> currencies;
        try {
            currencies = entityManager.createQuery("SELECT c FROM Currency c WHERE CAST(c.code AS string) " +
                    "LIKE :code AND c.value LIKE :currencyValue ORDER BY c.value", Currency.class)
                    .setParameter("code", getLikeParam(code)).setParameter("currencyValue", getLikeParam(value))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage).getResultList();
            logger.info("Found " + currencies.size() + " of currencies");
            return currencies;
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
