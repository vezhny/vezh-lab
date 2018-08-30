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
    @Override
    Card getById(int id);

    @Transactional
    @Override
    void update(Card card);
}
