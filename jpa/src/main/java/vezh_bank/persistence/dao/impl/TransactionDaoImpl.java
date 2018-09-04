package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.TransactionDao;
import vezh_bank.persistence.entity.Transaction;
import vezh_bank.util.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> selectAll() {
        logger.info("Select all transactions");
        List<Transaction> transactions;
        try {
            transactions = entityManager.createQuery("SELECT t FROM Transaction t ORDER BY t.dateTime DESC",
                    Transaction.class).getResultList();
        } catch (NoResultException e) {
            transactions = new ArrayList<>();
        }
        logger.info("Found " + transactions.size() + " of transactions");
        return transactions;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all transactions");
        entityManager.createNativeQuery("DELETE FROM TRANSACTIONS").executeUpdate();
    }

    @Override
    public Transaction getById(int id) {
        logger.info("Select transaction with ID: " + id);
        try {
            Transaction transaction = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.id = :id",
                    Transaction.class).setParameter("id", id).getSingleResult();
            logger.info(transaction);
            return transaction;
        } catch (NoResultException e) {
            logger.info("Transaction with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public int selectCount() {
        logger.info("Select number of transactions");
        int numberOfTransactions = entityManager.createQuery("SELECT COUNT(*) FROM Transaction t",
                Long.class).getSingleResult().intValue();
        logger.info("Number of transactions: " + numberOfTransactions);
        return numberOfTransactions;
    }

    @Override
    public void insert(Transaction transaction) {
        logger.info("Insert transaction: " + transaction);
        entityManager.persist(transaction);
    }

    @Override
    public List<Transaction> select(String trxType, String dateTime, String data, String status) {
        logger.info("Select transaction");
        logger.info("Type: " + trxType);
        logger.info("Date&Time: " + dateTime);
        logger.info("Data: " + data);
        logger.info("Status: " + status);
        List<Transaction> transactions;
        try {
            transactions = entityManager.createQuery("SELECT t FROM Transaction t WHERE " +
                    "t.type LIKE :trxType AND t.dateTime LIKE :dateTime AND t.data LIKE :data " +
                    "AND t.status LIKE :status ORDER BY t.dateTime DESC", Transaction.class)
                    .setParameter("trxType", getLikeParam(trxType))
                    .setParameter("dateTime", getLikeParam(dateTime))
                    .setParameter("data", getLikeParam(data))
                    .setParameter("status", getLikeParam(status))
                    .getResultList();
        } catch (NoResultException e) {
            transactions = new ArrayList<>();
        }
        logger.info("Found " + transactions.size() + " of transactions");
        return transactions;
    }

    @Override
    public List<Transaction> select(int requiredPage, int rowsOnPage, String trxType, String dateTime,
                                    String data, String status) {
        logger.info("Select transaction");
        logger.info("Type: " + trxType);
        logger.info("Date&Time: " + dateTime);
        logger.info("Data: " + data);
        logger.info("Status: " + status);
        List<Transaction> transactions;
        try {
            transactions = entityManager.createQuery("SELECT t FROM Transaction t WHERE " +
                    "t.type LIKE :trxType AND t.dateTime LIKE :dateTime AND t.data LIKE :data " +
                    "AND t.status LIKE :status ORDER BY t.dateTime DESC", Transaction.class)
                    .setParameter("trxType", getLikeParam(trxType))
                    .setParameter("dateTime", getLikeParam(dateTime))
                    .setParameter("data", getLikeParam(data))
                    .setParameter("status", getLikeParam(status))
                    .setFirstResult(getFirstResultIndex(requiredPage, rowsOnPage))
                    .setMaxResults(rowsOnPage)
                    .getResultList();
        } catch (NoResultException e) {
            transactions = new ArrayList<>();
        }
        logger.info("Found " + transactions.size() + " of transactions");
        return transactions;
    }

    @Override
    public int selectCount(String trxType, String dateTime, String data, String status) {
        logger.info("Select number of transactions");
        logger.info("Type: " + trxType);
        logger.info("Date&Time: " + dateTime);
        logger.info("Data: " + data);
        logger.info("Status: " + status);
        int transactions = 0;
        try {
            transactions = entityManager.createQuery("SELECT COUNT(*) FROM Transaction t WHERE " +
                    "t.type LIKE :trxType AND t.dateTime LIKE :dateTime AND t.data LIKE :data " +
                    "AND t.status LIKE :status", Long.class)
                    .setParameter("trxType", getLikeParam(trxType))
                    .setParameter("dateTime", getLikeParam(dateTime))
                    .setParameter("data", getLikeParam(data))
                    .setParameter("status", getLikeParam(status))
                    .getSingleResult().intValue();
        } catch (NoResultException e) {
        }
        logger.info("Found " + transactions + " of transactions");
        return transactions;
    }
}
