package vezh_bank.persistence.dao.impl;

import org.apache.log4j.Logger;
import vezh_bank.persistence.dao.CardBrandDao;
import vezh_bank.persistence.entity.CardBrand;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class CardBrandDaoImpl implements CardBrandDao {
    private Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(CardBrand cardBrand) {
        logger.info("Insert card brand: " + cardBrand);
        entityManager.persist(cardBrand);
    }

    @Override
    public List<CardBrand> selectAll() {
        logger.info("Select all card brands");
        List<CardBrand> cardBrands;
        try {
            cardBrands = entityManager.createQuery(
                    "SELECT cb FROM CardBrand cb ORDER BY cb.name", CardBrand.class).getResultList();
        } catch (NoResultException e) {
            cardBrands = new ArrayList<>();
        }
        logger.info("Found " + cardBrands.size() + " of card brands");
        return cardBrands;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete all card brands");
        entityManager.clear();
    }

    @Override
    public CardBrand getById(int id) {
        logger.info("Select card brand by ID: " + id);
        try {
            CardBrand cardBrand = entityManager.createQuery(
                    "SELECT cb FROM CardBrand cb WHERE cb.id = :id",
                    CardBrand.class).setParameter("id", id).getSingleResult();
            logger.info(cardBrand);
            return cardBrand;
        } catch (NoResultException e) {
            logger.info("Card brand with ID " + id + " doesn't exist");
            return null;
        }
    }

    @Override
    public void update(CardBrand cardBrand) {
        logger.info("Update card brand: " + cardBrand);
        entityManager.merge(cardBrand);
    }

    @Override
    public void delete(CardBrand cardBrand) {
        logger.info("Delete card brand: " + cardBrand);
        entityManager.remove(cardBrand);
    }
}
