package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.PaymentDao;
import vezh_bank.persistence.entity.Payment;
import vezh_bank.util.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteAll() {
        logger.info("Delete all payments");
        entityManager.createNativeQuery("DELETE FROM PAYMENTS").executeUpdate();
    }

    @Override
    public void delete(Payment payment) {
        logger.info("Delete payment: " + payment);
        entityManager.createNativeQuery("DELETE FROM PAYMENTS WHERE PAYMENT_ID = ?")
                .setParameter(1, payment.getId()).executeUpdate();
    }

    @Override
    public void update(Payment payment) {
        logger.info("Update payment: " + payment);
        entityManager.merge(payment);
    }

    @Override
    public void insert(Payment payment) {
        logger.info("Insert payment: " + payment);
        entityManager.persist(payment);
    }

    @Override
    public int selectCount() {
        logger.info("Select number of payments");
        int numberOfPayments = entityManager.createQuery("SELECT COUNT(*) FROM Payment p", Long.class)
                .getSingleResult().intValue();
        logger.info("Number of payments: " + numberOfPayments);
        return numberOfPayments;
    }

    @Override
    public List<Payment> selectAll() {
        logger.info("Select all payments");
        List<Payment> payments;
        try {
            payments = entityManager.createQuery("SELECT p FROM Payment p ORDER BY p.name", Payment.class).getResultList();
        } catch (NoResultException e) {
            payments = new ArrayList<>();
        }
        logger.info("Found " + payments.size() + " of payments");
        return payments;
    }

    @Override
    public Payment getById(int id) {
        logger.info("Select payment with ID: " + id);
        try {
            Payment payment = entityManager.createQuery("SELECT p FROM Payment p WHERE p.id = :id", Payment.class)
                    .setParameter("id", id).getSingleResult();
            logger.info(payment);
            return payment;
        } catch (NoResultException e) {
            logger.info("Payment with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public void delete(int id) {
        logger.info("Delete payment with ID: " + id);
        entityManager.createNativeQuery("DELETE FROM PAYMENTS WHERE PAYMENT_ID = ?")
                .setParameter(1, id)
                .executeUpdate();
    }

    @Override
    public List<Payment> select(String currencyCode) {
        logger.info("Select payments where currency code: " + currencyCode);
        List<Payment> payments;
        try {
            payments = entityManager.createQuery("SELECT p FROM Payment p WHERE " +
                    "CAST(p.currency.code AS string) LIKE :currency ORDER BY p.name", Payment.class)
                    .setParameter("currency", getLikeParam(currencyCode))
                    .getResultList();
        } catch (NoResultException e) {
            payments = new ArrayList<>();
        }
        logger.info("Found " + payments.size() + " of payments");
        return payments;
    }

    @Override
    public List<Payment> select(String name, String currency) {
        logger.info("Select payments");
        logger.info("Name: " + name);
        logger.info("Currency code: " + currency);
        List<Payment> payments;
        try {
            payments = entityManager.createQuery("SELECT p FROM Payment p WHERE " +
                    "CAST(p.currency.code AS string) LIKE :currency AND p.name LIKE :paymentName ORDER BY p.name", Payment.class)
                    .setParameter("currency", getLikeParam(currency))
                    .setParameter("paymentName", getLikeParam(name))
                    .getResultList();
        } catch (NoResultException e) {
            payments = new ArrayList<>();
        }
        logger.info("Found " + payments.size() + " of payments");
        return payments;
    }

    @Override
    public List<Payment> select(int requiredPage, int rowsOnPage, String name, String currency) {
        logger.info("Select payments");
        logger.info("Required page: " + requiredPage);
        logger.info("Rows on page: " + rowsOnPage);
        logger.info("Name: " + name);
        logger.info("Currency code: " + currency);
        List<Payment> payments;
        try {
            payments = entityManager.createQuery("SELECT p FROM Payment p WHERE " +
                    "CAST(p.currency.code AS string) LIKE :currency AND p.name LIKE :paymentName ORDER BY p.name", Payment.class)
                    .setParameter("currency", getLikeParam(currency))
                    .setParameter("paymentName", getLikeParam(name))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage)
                    .getResultList();
        } catch (NoResultException e) {
            payments = new ArrayList<>();
        }
        logger.info("Found " + payments.size() + " of payments");
        return payments;
    }
}