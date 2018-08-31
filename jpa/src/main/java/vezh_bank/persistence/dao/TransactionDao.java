package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public interface TransactionDao extends GlobalSelectDao<Transaction> {
    @Transactional
    @Override
    List<Transaction> selectAll();

    @Transactional
    void deleteAll();

    @Transactional
    @Override
    Transaction getById(int id);

    @Transactional
    int selectCount();

    @Transactional
    void insert(Transaction transaction);

    @Transactional
    List<Transaction> select(String trxType, String dateTime, String data, String status);

    @Transactional
    List<Transaction> select(int requiredPage, int rowsOnPage, String trxType,
                             String dateTime, String data, String status);

    @Transactional
    int selectCount(String trxType, String dateTime, String data, String status);
}
