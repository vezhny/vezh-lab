package vezh_bank.extended_tests;

import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.*;

public class PersistenceTest extends RootTest {

    @Autowired
    protected DataBaseService dataBaseService;

    @Step("Create currency with code \"{0}\", value \"{1}\"")
    protected void createCurrency(int code, String value) {
        dataBaseService.getCurrencyDao().insert(new Currency(code, value));
    }

    @Step("Create currency \"{0}\"")
    protected void createCurrency(Currency currency) {
        createCurrency(currency.getCode(), currency.getValue());
    }

    @Step("Create event. {0}")
    protected void createEvent(Event event) {
        dataBaseService.getEventDao().insert(event);
    }

    @Step("Create payment. {0}")
    protected void createPayment(Payment payment) {
        dataBaseService.getPaymentDao().insert(payment);
    }

    @Step("Create transaction. {0}")
    protected void createTransaction(Transaction transaction) {
        dataBaseService.getTransactionDao().insert(transaction);
    }

    @Step("Create user. {0}")
    protected void createUser(User user) {
        dataBaseService.getUserDao().insert(user);
    }

    @Step("Create user request. {0}")
    protected void createUserRequest(UserRequest userRequest) {
        dataBaseService.getUserRequestDao().insert(userRequest);
    }

    @Step("Create card. {0}")
    protected void createCard(Card card) {
        dataBaseService.getCardDao().insert(card);
    }

}
