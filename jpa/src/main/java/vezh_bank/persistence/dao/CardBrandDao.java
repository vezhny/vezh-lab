package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.CardBrand;

import javax.transaction.Transactional;
import java.util.List;

public interface CardBrandDao extends GlobalDao<CardBrand> {
    @Transactional
    @Override
    void insert(CardBrand cardBrand);

    @Transactional
    @Override
    List<CardBrand> selectAll();

    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    CardBrand getById(int id);

    @Transactional
    @Override
    void update(CardBrand cardBrand);

    @Transactional
    @Override
    void delete(CardBrand cardBrand);
}
