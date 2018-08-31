package vezh_bank.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.dao.*;
import vezh_bank.util.Logger;

@Service
public class DataBaseService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRequestDao userRequestDao;

    @Autowired
    private PaymentDao paymentDao;

    public CardDao getCardDao() {
        return cardDao;
    }

    public CurrencyDao getCurrencyDao() {
        return currencyDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserRequestDao getUserRequestDao() {
        return userRequestDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }
}
