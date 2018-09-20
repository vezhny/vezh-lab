package vezh_bank.extended_tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import vezh_bank.enums.*;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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

    @Step("Check currency. Expected code: {0}. Expected value: {1}. Actual currency: {3}")
    protected void checkCurrency(int expectedCode, String expectedValue,
                               Currency actualCurrency) {
        Assertions.assertEquals(expectedCode, actualCurrency.getCode(),
                "Currency code");
        Assertions.assertEquals(expectedValue, actualCurrency.getValue(),
                "Currency value");
    }

    protected void checkCurrenciesCount(int expectedCount, List<Currency> actualCurrencyList) {
        checkItemsCount(expectedCount, actualCurrencyList, "Currencies count");
    }

    @Step("Create event. {0}")
    protected void createEvent(Event event) {
        dataBaseService.getEventDao().insert(event);
    }

    @Step("Check event. Expected type: {0}. Should contain data: {1}. Actual event: {2}")
    protected void checkEvent(EventType expectedEventType, String containingData, Event actualEvent) {
        Assertions.assertEquals(expectedEventType.toString(), actualEvent.getType(), "Event type");
        Assertions.assertTrue(actualEvent.getData().contains(containingData), "Event data");
        Assertions.assertNotNull(actualEvent.getDate(), "Event date");
    }

    protected void checkEventsCount(int expectedEventCount, List<Event> events) {
        checkItemsCount(expectedEventCount, events, "Number of events");
    }

    @Step("Create payment. {0}")
    protected void createPayment(Payment payment) {
        dataBaseService.getPaymentDao().insert(payment);
    }

    @Step("Check payment. Expected name: {0}. Expected description: {1}. Expected min amount: {2}. " +
            "Expected max amount: {3}. Expected currency: {4}. Actual payment: {5}")
    protected void checkPayment(String expectedName, String expectedDescription, BigDecimal expectedMinAmount,
                                BigDecimal expectedMaxAmount, BigDecimal expectedCommission, Currency expectedCurrency,
                                Payment actualPayment) {
        Assertions.assertEquals(expectedName, actualPayment.getName(), "Payment name");
        Assertions.assertEquals(expectedDescription, actualPayment.getDescription(), "Payment description");
        Assertions.assertEquals(expectedMinAmount, actualPayment.getMinAmount(), "Payment min amount");
        Assertions.assertEquals(expectedMaxAmount, actualPayment.getMaxAmount(), "Payment max amount");
        Assertions.assertEquals(expectedCommission, actualPayment.getCommission(), "Payment commission");
        checkCurrency(expectedCurrency.getCode(), expectedCurrency.getValue(), actualPayment.getCurrency());
    }

    protected void checkPaymentCount(int expectedPaymentCount, List<Payment> payments) {
        checkItemsCount(expectedPaymentCount, payments, "Number of payments");
    }

    @Step("Create transaction. {0}")
    protected void createTransaction(Transaction transaction) {
        dataBaseService.getTransactionDao().insert(transaction);
    }

    @Step("Check transaction. Expected type: {0}. Expected data: {1}. Expected status: {2}. Actual transaction: {3}")
    protected void checkTransaction(TransactionType expectedTrxType, String expectedTrxData,
                                    TransactionStatus expectedTrxStatus, Transaction actualTrx) {
        Assertions.assertEquals(expectedTrxType.toString(), actualTrx.getType(), "Transaction type");
        Assertions.assertEquals(expectedTrxData, actualTrx.getData(), "Transaction data");
        Assertions.assertEquals(expectedTrxStatus.toString(), actualTrx.getStatus(), "Transaction status");
    }

    protected void checkTransactionCount(int expectedTransactionsCount, List<Transaction> transactions) {
        checkItemsCount(expectedTransactionsCount, transactions, "Number of transactions");
    }

    @Step("Create user. {0}")
    protected void createUser(User user) {
        dataBaseService.getUserDao().insert(user);
    }

    @Step("Check user. Expected login: {0}. Expected password: {1}. Expected role: {2}. Expected config: {3}. " +
            "Expected attempts to sign in: {4}. Expected blocked status: {5}. Expected data: {7}. Actual user: {8}")
    protected void checkUser(String expectedLogin, String expectedPassword, UserRole expectedRole,
                             String expectedConfig, int expectedAttemptsToSignInLeft, boolean expectedBlocked,
                             String expectedLastSignInDate, String expectedData, User actualUser) {
        Assertions.assertEquals(expectedLogin, actualUser.getLogin(), "User login");
        Assertions.assertEquals(expectedPassword, actualUser.getPassword(), "User password");
        Assertions.assertEquals(expectedRole.getName(), actualUser.getRole(), "User role");
        Assertions.assertEquals(expectedConfig, actualUser.getConfig(), "User config");
        Assertions.assertEquals(expectedAttemptsToSignInLeft, actualUser.getAttemptsToSignIn(),
                "User attempts to sign in");
        Assertions.assertEquals(expectedBlocked, actualUser.isBlocked(), "User blocking");
        Assertions.assertEquals(expectedLastSignInDate, actualUser.getLastSignIn(), "User last sign in date");
        Assertions.assertEquals(expectedData, actualUser.getData(), "User data");
    }

    protected void checkUsersCount(int expectedCount, List<User> users) {
        checkItemsCount(expectedCount, users, "Number of users");
    }

    @Step("Create user request. {0}")
    protected void createUserRequest(UserRequest userRequest) {
        dataBaseService.getUserRequestDao().insert(userRequest);
    }

    @Step("Check user request. Expected user ID: {0}. Expected status: {1}. Expected data: {2}. Actual request: {3}")
    protected void checkUserRequest(int expectedUserId, UserRequestStatus expectedRequestStatus,
                                    String expectedData, UserRequest actualUserRequest) {
        Assertions.assertEquals(expectedUserId, actualUserRequest.getUser().getId(), "User ID");
        Assertions.assertEquals(expectedRequestStatus.toString(), actualUserRequest.getStatus(), "Status");
        Assertions.assertEquals(expectedData, actualUserRequest.getData(), "Data");
    }

    protected void checkUserRequestsCount(int expectedCount, List<UserRequest> userRequests) {
        checkItemsCount(expectedCount, userRequests, "Number of user requests");
    }

    @Step("Create card. {0}")
    protected void createCard(Card card) {
        dataBaseService.getCardDao().insert(card);
    }

    @Step("Check card. Expected PAN: {0}. Expected holder: {1}. Expected CVC: {2}. Expected expiry: {3}." +
            " Expected currency: {4}. Expected status: {5}. Expected amount: {6}. Actual card: {7}")
    protected void checkCard(String expectedPan, User expectedHolder, int expectedCvc,
                             String expectedExpiry, Currency expectedCurrency, CardStatus expectedStatus,
                             BigDecimal expectedAmount, Card actualCard) {
        Assertions.assertEquals(expectedPan, actualCard.getPan(), "PAN");
        Assertions.assertEquals(expectedHolder.toString(), actualCard.getHolder().toString(), "Holder");
        Assertions.assertEquals(expectedCvc, actualCard.getCvc(), "CVC");
        Assertions.assertEquals(expectedExpiry, actualCard.getExpiry(), "Expiry");
        Assertions.assertEquals(expectedCurrency.toString(), actualCard.getCurrency().toString(), "Currency");
        Assertions.assertEquals(expectedStatus.toString(), actualCard.getStatus(), "Status");
        Assertions.assertEquals(expectedAmount, actualCard.getAmount(), "Amount");
    }

    protected void checkCardsCount(int expectedCount, List<Card> cards) {
        checkItemsCount(expectedCount, cards, "Number of cards");
    }

    @Step("Check {2}. Expected count: {0}")
    private void checkItemsCount(int expectedCount, Collection collection, String message) {
        Assertions.assertEquals(expectedCount, collection.size(), message);
    }
}
