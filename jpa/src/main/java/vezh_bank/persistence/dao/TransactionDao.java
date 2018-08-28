package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public interface TransactionDao extends GlobalDao<Transaction> {
    @Transactional
    @Override
    List<Transaction> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(Transaction transaction);

    @Transactional
    @Override
    Transaction getById(int id);

    @Transactional
    @Override
    void update(Transaction transaction);
}
