package vezh_bank.persistence.dao.impl;

import vezh_bank.persistence.dao.CardBrandDao;
import vezh_bank.persistence.entity.CardBrand;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CardBrandDaoImpl implements CardBrandDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CardBrand> selectAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public CardBrand getById(int id) {
        return null;
    }

    @Override
    public void update(CardBrand cardBrand) {

    }

    @Override
    public void delete(CardBrand cardBrand) {

    }
}
