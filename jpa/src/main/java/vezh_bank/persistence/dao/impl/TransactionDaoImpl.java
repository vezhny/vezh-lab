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
            transactions = entityManager.createQuery("SELECT t FROM Transaction t ORDER BY t.dateTime", Transaction.class).getResultList();
        } catch (NoResultException e) {
            transactions = new ArrayList<>();
        }
        logger.info("Found " + transactions.size() + " of transactions");
        return transactions;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all transactions");
        entityManager.clear();
    }

    @Override
    public void delete(Transaction transaction) {
        logger.info("Delete transaction: " + transaction);
        entityManager.remove(transaction);
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
        int numberOfTransactions = entityManager.createQuery("SELECT COUNT(*) FROM Transaction t", Long.class).getSingleResult().intValue();
        logger.info("Number of transactions: " + numberOfTransactions);
        return numberOfTransactions;
    }

    @Override
    public void update(Transaction transaction) {
        logger.info("Update transaction: " + transaction);
        entityManager.merge(transaction);
    }

    @Override
    public void insert(Transaction transaction) {
        logger.info("Insert transaction: " + transaction);
        entityManager.persist(transaction);
    }
}
