package vezh_bank.assertions;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.EventData;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.mock.web.MockHttpServletResponse;
import vezh_bank.constants.Headers;
import vezh_bank.enums.*;
import vezh_bank.persistence.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class Assert {

    @Step("Check {1} is not null")
    public void checkNotNull(Object value, String message) {
        Assertions.assertNotNull(value, message);
    }

    @Step("Check {1} is null")
    public void checkNull(Object value, String message) {
        Assertions.assertNull(value, message);
    }

    @Step("Check exception. Expected {0}")
    public void checkException(Class expectedExceptionClass, Executable runnable) {
        Assertions.assertThrows(expectedExceptionClass, runnable);
    }

    @Step("Check {2}. Excepted: {0}. Actual {1}")
    public void check(Object expected, Object actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    @Step("Check response code. Expected {0}. Actual {1}")
    public void checkResponseCode(int expectedCode, int actualCode) {
        Assertions.assertEquals(expectedCode, actualCode, "Response code");
    }

    @Step("Check currency. Expected code: {0}. Expected value: {1}. Actual currency: {3}")
    public void checkCurrency(int expectedCode, String expectedValue,
                                 Currency actualCurrency) {
        Assertions.assertEquals(expectedCode, actualCurrency.getCode(),
                "Currency code");
        Assertions.assertEquals(expectedValue, actualCurrency.getValue(),
                "Currency value");
    }

    public void checkCurrenciesCount(int expectedCount, List<Currency> actualCurrencyList) {
        checkItemsCount(expectedCount, actualCurrencyList, "Currencies count");
    }

    @Step("Check event. Expected type: {0}. Should contain data: {1}. Actual event: {2}")
    public void checkEvent(EventType expectedEventType, String containingData, Event actualEvent) {
        Assertions.assertEquals(expectedEventType.toString(), actualEvent.getType(), "Event type");
        Assertions.assertTrue(actualEvent.getData().contains(containingData), "Event data");
        Assertions.assertNotNull(actualEvent.getDate(), "Event date");
    }

    public void checkEventsCount(int expectedEventCount, List<Event> events) {
        checkItemsCount(expectedEventCount, events, "Number of events");
    }

    @Step("Check payment. Expected name: {0}. Expected description: {1}. Expected min amount: {2}. " +
            "Expected max amount: {3}. Expected currency: {4}. Actual payment: {5}")
    public void checkPayment(String expectedName, String expectedDescription, BigDecimal expectedMinAmount,
                                BigDecimal expectedMaxAmount, BigDecimal expectedCommission, Currency expectedCurrency,
                                Payment actualPayment) {
        Assertions.assertEquals(expectedName, actualPayment.getName(), "Payment name");
        Assertions.assertEquals(expectedDescription, actualPayment.getDescription(), "Payment description");
        Assertions.assertEquals(expectedMinAmount, actualPayment.getMinAmount(), "Payment min amount");
        Assertions.assertEquals(expectedMaxAmount, actualPayment.getMaxAmount(), "Payment max amount");
        Assertions.assertEquals(expectedCommission, actualPayment.getCommission(), "Payment commission");
        checkCurrency(expectedCurrency.getCode(), expectedCurrency.getValue(), actualPayment.getCurrency());
    }

    public void checkPaymentCount(int expectedPaymentCount, List<Payment> payments) {
        checkItemsCount(expectedPaymentCount, payments, "Number of payments");
    }

    @Step("Check transaction. Expected type: {0}. Expected data: {1}. Expected status: {2}. Actual transaction: {3}")
    public void checkTransaction(TransactionType expectedTrxType, String expectedTrxData,
                                    TransactionStatus expectedTrxStatus, Transaction actualTrx) {
        Assertions.assertEquals(expectedTrxType.toString(), actualTrx.getType(), "Transaction type");
        Assertions.assertEquals(expectedTrxData, actualTrx.getData(), "Transaction data");
        Assertions.assertEquals(expectedTrxStatus.toString(), actualTrx.getStatus(), "Transaction status");
    }

    public void checkTransactionCount(int expectedTransactionsCount, List<Transaction> transactions) {
        checkItemsCount(expectedTransactionsCount, transactions, "Number of transactions");
    }

    @Step("Check user. Expected login: {0}. Expected password: {1}. Expected role: {2}. Expected config: {3}. " +
            "Expected attempts to sign in: {4}. Expected blocked status: {5}. Expected data: {7}. Actual user: {8}")
    public void checkUser(String expectedLogin, String expectedPassword, UserRole expectedRole,
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

    public void checkUsersCount(int expectedCount, List<User> users) {
        checkItemsCount(expectedCount, users, "Number of users");
    }

    @Step("Check user request. Expected user ID: {0}. Expected status: {1}. Expected data: {2}. Actual request: {3}")
    public void checkUserRequest(int expectedUserId, UserRequestStatus expectedRequestStatus,
                                    String expectedData, UserRequest actualUserRequest) {
        Assertions.assertEquals(expectedUserId, actualUserRequest.getUser().getId(), "User ID");
        Assertions.assertEquals(expectedRequestStatus.toString(), actualUserRequest.getStatus(), "Status");
        Assertions.assertEquals(expectedData, actualUserRequest.getData(), "Data");
    }

    public void checkUserRequestsCount(int expectedCount, List<UserRequest> userRequests) {
        checkItemsCount(expectedCount, userRequests, "Number of user requests");
    }

    @Step("Check card. Expected PAN: {0}. Expected holder: {1}. Expected CVC: {2}. Expected expiry: {3}." +
            " Expected currency: {4}. Expected status: {5}. Expected amount: {6}. Actual card: {7}")
    public void checkCard(String expectedPan, User expectedHolder, int expectedCvc,
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

    public void checkCardsCount(int expectedCount, List<Card> cards) {
        checkItemsCount(expectedCount, cards, "Number of cards");
    }

    @Step("Check {2}. Expected count: {0}")
    public void checkItemsCount(int expectedCount, Collection collection, String message) {
        Assertions.assertEquals(expectedCount, collection.size(), message);
    }

    @Step("Check number of events. Expected {0}. Actual {1}")
    public void checkNumberOfEvents(int expected, int actual) {
        checkNumber(expected, actual, "Number of events");
    }

    @Step("Check number of users. Expected {0}. Actual {1}")
    public void checkNumberOfUsers(int expected, int actual) {
        checkNumber(expected, actual, "Number of users");
    }

    @Step("Check {2}. Expected: {0}. Actual: {1}")
    public void checkNumber(int expected, int actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    @Step("Check user. Expected login: {0}. Expected password: {1}. Expected role: {2}. Expected config: {3}. " +
            "Expected attempts to sign in: {4}. Expected blocked status: {5}. Expected data: {7}. Actual user: {8}")
    public void checkUser(String expectedLogin, String expectedPassword, UserRoleDTO expectedRole,
                             UserConfig expectedUserConfig, int expectedAttemptsToSignIn,
                             boolean expectedBlocked, String expectedLastSignIn,
                             UserData expectedUserData, User actualUser) {
        UserDTO userDTO = new UserDTO(actualUser);

        Assertions.assertEquals(expectedLogin, userDTO.getLogin(), "Login");
        Assertions.assertEquals(expectedPassword, userDTO.getPassword(), "Password");
        Assertions.assertEquals(expectedRole.getName(), userDTO.getRole().getName(), "Role");
        Assertions.assertEquals(expectedUserConfig.toString(), userDTO.getConfig().toString(), "Config");
        Assertions.assertEquals(expectedAttemptsToSignIn, userDTO.getAttemptsToSignIn(), "Attempts to sign in");
        Assertions.assertEquals(expectedBlocked, userDTO.isBlocked(), "Blocked");
        Assertions.assertEquals(expectedLastSignIn, userDTO.getLastSignIn(), "Last sign in");
        Assertions.assertEquals(expectedUserData.toString(), userDTO.getData().toString(), "Data");
    }

    @Step("Check event. Expected type: {0}. Expected data: {1}. Actual event: {2}")
    public void checkEvent(EventType expectedEventType, EventData expectedEventData, Event actualEvent) {
        EventDTO eventDTO = new EventDTO(actualEvent);

        Assertions.assertNotNull(eventDTO.getDate(), "Date");
        Assertions.assertEquals(expectedEventType, eventDTO.getType());
        Assertions.assertEquals(expectedEventData.toString(), eventDTO.getEventData().toString());
    }

    @Step("Check exception message. Excepted: {0}. Actual: {1}")
    public void checkExceptionMessage(String expected, MockHttpServletResponse response) {
        Assertions.assertEquals(expected,
                response.getHeader(Headers.ERROR_MESSAGE),
                "Exception message");
    }

    @Step("Check current page. Expected {0}. Actual {1}")
    public void checkCurrentPage(int expected, MockHttpServletResponse response) {
        Assertions.assertEquals(String.valueOf(expected), response.getHeader(Headers.CURRENT_PAGE),
                "Current page");
    }

    @Step("Check pages count. Expected {0}. Actual {1}")
    public void checkPagesCount(int expected, MockHttpServletResponse response) {
        Assertions.assertEquals(String.valueOf(expected), response.getHeader(Headers.PAGES_COUNT),
                "Pages count");
    }
}
