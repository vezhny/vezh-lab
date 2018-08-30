package vezh_bank.persistence.dao;

import vezh_bank.persistence.entity.Payment;

import javax.transaction.Transactional;
import java.util.List;

public interface PaymentDao extends GlobalDao<Payment> {
    @Transactional
    @Override
    void deleteAll();

    @Transactional
    @Override
    void delete(Payment payment);

    @Transactional
    @Override
    void update(Payment payment);

    @Transactional
    @Override
    void insert(Payment payment);

    @Transactional
    @Override
    int selectCount();

    @Transactional
    @Override
    List<Payment> selectAll();

    @Transactional
    @Override
    Payment getById(int id);
}
