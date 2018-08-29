package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Currency;

import javax.transaction.Transactional;
import java.util.List;

public interface CurrencyDao extends GlobalDao<Currency> {
    @Transactional
    @Override
    void insert(Currency currency);

    @Transactional
    @Override
    List<Currency> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(Currency currency);

    @Transactional
    void delete(int code);

    @Transactional
    void delete(String value);

    @Transactional
    @Override
    Currency getById(int id);

    @Transactional
    Currency getByValue(String value);

    @Transactional
    @Override
    void update(Currency currency);
}
