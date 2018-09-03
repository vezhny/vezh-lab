package vezh_bank.extended_tests;

import core.config.VezhBankConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vezh_bank.TestUtils;
import vezh_bank.constants.MavenProfiles;
import vezh_bank.enums.*;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.*;
import vezh_bank.util.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VezhBankConfiguration.class)
@WebAppConfiguration
@ActiveProfiles(MavenProfiles.TEST)
public class PersistenceTest {
    protected static Logger logger = Logger.getLogger(PersistenceTest.class);

    protected TestUtils testUtils;
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DataBaseService dataBaseService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUtils = new TestUtils(logger);
    }

    @AfterEach
    public void tearDown() {
        testUtils.logTestEnd();
        dataBaseService.getCurrencyDao().deleteAll();
        dataBaseService.getEventDao().deleteAll();
        dataBaseService.getPaymentDao().deleteAll();
        dataBaseService.getTransactionDao().deleteAll();
        dataBaseService.getUserDao().deleteAll();
        dataBaseService.getUserRequestDao().deleteAll();
        dataBaseService.getCardDao().deleteAll();
    }

    protected void createCurrency(int code, String value) {
        dataBaseService.getCurrencyDao().insert(new Currency(code, value));
    }

    protected void createCurrency(Currency currency) {
        createCurrency(currency.getCode(), currency.getValue());
    }

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

    protected void createEvent(Event event) {
        dataBaseService.getEventDao().insert(event);
    }

    protected void checkEvent(EventType expectedEventType, String containingData, Event actualEvent) {
        Assertions.assertEquals(expectedEventType.toString(), actualEvent.getType(), "Event type");
        Assertions.assertTrue(actualEvent.getData().contains(containingData), "Event data");
        Assertions.assertNotNull(actualEvent.getDate(), "Event date");
    }

    protected void checkEventsCount(int expectedEventCount, List<Event> events) {
//        Assertions.assertEquals(expectedEventCount, events.size(), "Number of events");
        checkItemsCount(expectedEventCount, events, "Number of events");
    }

    protected void createPayment(Payment payment) {
        dataBaseService.getPaymentDao().insert(payment);
    }

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

    protected void createTransaction(Transaction transaction) {
        dataBaseService.getTransactionDao().insert(transaction);
    }

    protected void checkTransaction(TransactionType expectedTrxType, String expectedTrxData,
                                    TransactionStatus expectedTrxStatus, Transaction actualTrx) {
        Assertions.assertEquals(expectedTrxType.toString(), actualTrx.getType(), "Transaction type");
        Assertions.assertEquals(expectedTrxData, actualTrx.getData(), "Transaction data");
        Assertions.assertEquals(expectedTrxStatus.toString(), actualTrx.getStatus(), "Transaction status");
    }

    protected void checkTransactionCount(int expectedTransactionsCount, List<Transaction> transactions) {
        checkItemsCount(expectedTransactionsCount, transactions, "Number of transactions");
    }

    protected void createUser(User user) {
        dataBaseService.getUserDao().insert(user);
    }

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

    protected void createUserRequest(UserRequest userRequest) {
        dataBaseService.getUserRequestDao().insert(userRequest);
    }

    protected void checkUserRequest(int expectedUserId, UserRequestStatus expectedRequestStatus,
                                    String expectedData, UserRequest actualUserRequest) {
        Assertions.assertEquals(expectedUserId, actualUserRequest.getUser().getId(), "User ID");
        Assertions.assertEquals(expectedRequestStatus.toString(), actualUserRequest.getStatus(), "Status");
        Assertions.assertEquals(expectedData, actualUserRequest.getData(), "Data");
    }

    protected void checkUserRequestsCount(int expectedCount, List<UserRequest> userRequests) {
        checkItemsCount(expectedCount, userRequests, "Number of user requests");
    }

    protected void createCard(Card card) {
        dataBaseService.getCardDao().insert(card);
    }

    protected void checkCard(String expectedPan, User expectedHolder, String expectedCvc,
                             String expectedExpiry, Currency expectedCurrency, CardStatus expectedStatus,
                             Card actualCard) {
        Assertions.assertEquals(expectedPan, actualCard.getPan(), "PAN");
        Assertions.assertEquals(expectedHolder, actualCard.getHolder(), "Holder");
        Assertions.assertEquals(expectedCvc, actualCard.getCvc(), "CVC");
        Assertions.assertEquals(expectedExpiry, actualCard.getExpiry(), "Expiry");
        Assertions.assertEquals(expectedCurrency, actualCard.getCurrency(), "Currency");
        Assertions.assertEquals(expectedStatus.toString(), actualCard.getStatus(), "Status");
        checkUser(expectedHolder.getLogin(), expectedHolder.getPassword(), expectedHolder.getRole(),
                expectedHolder.getConfig(), expectedHolder.getAttemptsToSignIn(), expectedHolder.isBlocked(),
                expectedHolder.getLastSignIn(), expectedHolder.getData(), actualCard.getHolder());
        checkCurrency(expectedCurrency.getCode(), expectedCurrency.getValue(), actualCard.getCurrency());
    }

    protected void checkCardsCount(int expectedCount, List<Card> cards) {
        checkItemsCount(expectedCount, cards, "Number of cards");
    }

    private void checkItemsCount(int expectedCount, Collection collection, String message) {
        Assertions.assertEquals(expectedCount, collection.size(), message);
    }
}