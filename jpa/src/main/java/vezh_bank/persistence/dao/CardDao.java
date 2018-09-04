package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Card;

import javax.transaction.Transactional;
import java.util.List;

public interface CardDao extends GlobalDao<Card> {
    @Transactional
    @Override
    void insert(Card card);

    @Transactional
    @Override
    int selectCount();

    @Transactional
    @Override
    List<Card> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(Card card);

    @Transactional
    void delete(int cardId);

    @Transactional
    @Override
    Card getById(int id);

    @Transactional
    @Override
    void update(Card card);

    @Transactional
    List<Card> select(int holderId);

    @Transactional
    List<Card> select(String pan, String holderName, String creationDate, String expiry, String currency,
                      String status);

    @Transactional
    int selectCount(String pan, String holderName, String creationDate, String expiry, String currency,
                    String status);

    @Transactional
    List<Card> select(int requiredPage, int rowsOnPage, String pan, String holderName, String creationDate,
                      String expiry, String currency, String status);
}
