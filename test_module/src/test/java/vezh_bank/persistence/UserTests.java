package vezh_bank.persistence;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.CardStatus;
import vezh_bank.enums.UserRequestStatus;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.*;
import vezh_bank.persistence.providers.user.SelectUserArgumentsProvider;
import vezh_bank.persistence.providers.user.SelectUserPageArgumentsProvider;

import java.util.List;

import static vezh_bank.constants.UserDefault.ATTEMPTS_TO_SIGN_IN;

@Epic("Persistence")
@Story("User persistence")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/5", url = "https://github.com/vezhny/vezh-lab/issues/5")
@Severity(SeverityLevel.BLOCKER)
public class UserTests extends PersistenceTest {

    @Feature("Insert entity")
    @Description("Insert user test")
    @Test
    public void insertUserTest() {
        testUtils.logTestStart("Insert user test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        List<User> users = dataBaseService.getUserDao().selectAll();
        userAsserts.checkUsersCount(1, users);
        userAsserts.checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, users.get(0));
    }

    @Feature("Select entity")
    @Description("Select user by ID test")
    @Test
    public void selectByIdUserTest() {
        testUtils.logTestStart("Select user by ID test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        List<User> users = dataBaseService.getUserDao().selectAll();
        user = dataBaseService.getUserDao().getById(users.get(0).getId());
        userAsserts.checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, user);
    }

    @Feature("Update entity")
    @Description("Update user test")
    @Test
    public void updateTest() {
        testUtils.logTestStart("Update user test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        user = dataBaseService.getUserDao().selectAll().get(0);
        String newData = "New data";
        user.setData(newData);
        dataBaseService.getUserDao().update(user);

        List<User> users = dataBaseService.getUserDao().selectAll();
        userAsserts.checkUsersCount(1, users);
        userAsserts.checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, newData, users.get(0));
    }

    @Feature("Delete entity")
    @Description("Delete user test")
    @Test
    public void deleteTest() {
        testUtils.logTestStart("Delete user test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        user = dataBaseService.getUserDao().selectAll().get(0);
        dataBaseService.getUserDao().delete(user);
        userAsserts.checkUsersCount(0, dataBaseService.getUserDao().selectAll());
    }

    @Feature("Delete entity")
    @Description("Delete user by id test")
    @Test
    public void deleteByIdTest() {
        testUtils.logTestStart("Delete user by id test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        user = dataBaseService.getUserDao().selectAll().get(0);
        dataBaseService.getUserDao().delete(user.getId());
        userAsserts.checkUsersCount(0, dataBaseService.getUserDao().selectAll());
    }

    @Feature("Select entity")
    @Description("Select user count test")
    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select user count test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        asserts.checkNumber(1, dataBaseService.getUserDao().selectCount(), "Number of users");
    }

    @Feature("Select entity")
    @Description("Select user with params test. Login: {0}, role: {1}, blocked: {2}, data: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectUserArgumentsProvider.class)
    public void selectTest(String login, String role, String blocked, String data,
                           int expectedUsersCount) {
        testUtils.logTestStart("Select user with params test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User user2 = new User("Login2", "password", userRoles.get(1),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User user3 = new User("Login3", "password", userRoles.get(2),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);
        User user4 = new User("Login4", "password", userRoles.get(0),
                "User data 4", "Config", ATTEMPTS_TO_SIGN_IN);
        User user5 = new User("Login5", "password", userRoles.get(1),
                "User data 5", "Config", ATTEMPTS_TO_SIGN_IN);
        User user6 = new User("Login6", "password", userRoles.get(2),
                "User data 6", "Config", ATTEMPTS_TO_SIGN_IN);
        User user7 = new User("Login7", "password", userRoles.get(0),
                "User data 7", "Config", ATTEMPTS_TO_SIGN_IN);
        User user8 = new User("Login8", "password", userRoles.get(1),
                "User data 8", "Config", ATTEMPTS_TO_SIGN_IN);
        User user9 = new User("Login9", "password", userRoles.get(2),
                "User data 9", "Config", ATTEMPTS_TO_SIGN_IN);
        User user10 = new User("Login0", "password", userRoles.get(0),
                "User data 0", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user1);
        createUser(user2);
        createUser(user3);
        createUser(user4);
        createUser(user5);
        createUser(user6);
        createUser(user7);
        createUser(user8);
        createUser(user9);
        createUser(user10);

        List<User> users = dataBaseService.getUserDao().select(login, role, blocked, data);
        userAsserts.checkUsersCount(expectedUsersCount, users);
    }

    @Feature("Select entity")
    @Description("Select user with pages test. Required page: {0}, rows on page: {1}, login: {2}, role: {3}, " +
            "blocked: {4}, data: {5}")
    @ParameterizedTest
    @ArgumentsSource(SelectUserPageArgumentsProvider.class)
    public void selectPageTest(int requiredPage, int rowsOnPage,
                               String login, String role, String blocked, String data,
                               int expectedUsersCount) {
        testUtils.logTestStart("Select user with params test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User user2 = new User("Login2", "password", userRoles.get(1),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User user3 = new User("Login3", "password", userRoles.get(2),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);
        User user4 = new User("Login4", "password", userRoles.get(0),
                "User data 4", "Config", ATTEMPTS_TO_SIGN_IN);
        User user5 = new User("Login5", "password", userRoles.get(1),
                "User data 5", "Config", ATTEMPTS_TO_SIGN_IN);
        User user6 = new User("Login6", "password", userRoles.get(2),
                "User data 6", "Config", ATTEMPTS_TO_SIGN_IN);
        User user7 = new User("Login7", "password", userRoles.get(0),
                "User data 7", "Config", ATTEMPTS_TO_SIGN_IN);
        User user8 = new User("Login8", "password", userRoles.get(1),
                "User data 8", "Config", ATTEMPTS_TO_SIGN_IN);
        User user9 = new User("Login9", "password", userRoles.get(2),
                "User data 9", "Config", ATTEMPTS_TO_SIGN_IN);
        User user10 = new User("Login0", "password", userRoles.get(0),
                "User data 0", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user1);
        createUser(user2);
        createUser(user3);
        createUser(user4);
        createUser(user5);
        createUser(user6);
        createUser(user7);
        createUser(user8);
        createUser(user9);
        createUser(user10);

        List<User> users = dataBaseService.getUserDao().select(requiredPage,
                rowsOnPage, login, role, blocked, data);
        userAsserts.checkUsersCount(expectedUsersCount, users);
    }

    @Feature("Select entity")
    @Description("Select user count with params test. Login: {0}, role: {1}, blocked: {2}, data: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectUserArgumentsProvider.class)
    public void selectCountTest(String login, String role, String blocked, String data,
                                int expectedUsersCount) {
        testUtils.logTestStart("Select user count with params test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User user2 = new User("Login2", "password", userRoles.get(1),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User user3 = new User("Login3", "password", userRoles.get(2),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);
        User user4 = new User("Login4", "password", userRoles.get(0),
                "User data 4", "Config", ATTEMPTS_TO_SIGN_IN);
        User user5 = new User("Login5", "password", userRoles.get(1),
                "User data 5", "Config", ATTEMPTS_TO_SIGN_IN);
        User user6 = new User("Login6", "password", userRoles.get(2),
                "User data 6", "Config", ATTEMPTS_TO_SIGN_IN);
        User user7 = new User("Login7", "password", userRoles.get(0),
                "User data 7", "Config", ATTEMPTS_TO_SIGN_IN);
        User user8 = new User("Login8", "password", userRoles.get(1),
                "User data 8", "Config", ATTEMPTS_TO_SIGN_IN);
        User user9 = new User("Login9", "password", userRoles.get(2),
                "User data 9", "Config", ATTEMPTS_TO_SIGN_IN);
        User user10 = new User("Login0", "password", userRoles.get(0),
                "User data 0", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user1);
        createUser(user2);
        createUser(user3);
        createUser(user4);
        createUser(user5);
        createUser(user6);
        createUser(user7);
        createUser(user8);
        createUser(user9);
        createUser(user10);

        int users = dataBaseService.getUserDao().selectCount(login, role,
                blocked, data);
        asserts.checkNumber(expectedUsersCount, users, "Number of users");
    }

    @Feature("Select entity")
    @Description("Get user requests test")
    @Test
    public void getUserRequestsTest() {
        testUtils.logTestStart("Get user requests test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        UserRequest userRequest = new UserRequest(user, "Request data 1");
        createUserRequest(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);

        user = dataBaseService.getUserDao().getById(user.getId());
        userRequestAsserts.checkUserRequest(user.getId(), UserRequestStatus.OPEN, userRequest.getData(), user.getUserRequests().get(0));
    }

    @Feature("Delete entity")
    @Description("Delete user request test")
    @Test
    public void deleteUserRequestTest() {
        testUtils.logTestStart("Delete user request test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        UserRequest userRequest = new UserRequest(user, "Request data 1");
        createUserRequest(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);
        dataBaseService.getUserRequestDao().delete(userRequest);

        user = dataBaseService.getUserDao().getById(user.getId());
        asserts.checkNumber(0, user.getUserRequests().size(), "User requests count");
    }

    @Feature("Select entity")
    @Description("Get cards test")
    @Test
    public void getCardsTest() {
        testUtils.logTestStart("Get cards test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        Currency cardCurrency = new Currency(643, "RUB");
        createCurrency(cardCurrency);

        Card card = new Card("5454545454545454", user, 111, "1020", cardCurrency);
        createCard(card);

        user = dataBaseService.getUserDao().getById(user.getId());
        asserts.checkNumber(1, user.getCards().size(), "Cards count");
        cardAsserts.checkCard(card.getPan(), user, card.getCvc(), card.getExpiry(), cardCurrency, CardStatus.ACTIVE, card.getAmount(),
                user.getCards().get(0));
    }

    @Feature("Delete entity")
    @Description("Delete card test")
    @Test
    public void deleteCardTest() {
        testUtils.logTestStart("Delete card test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        Currency cardCurrency = new Currency(643, "RUB");
        createCurrency(cardCurrency);

        Card card = new Card("5454545454545454", user, 111, "1020", cardCurrency);
        createCard(card);
        card = dataBaseService.getCardDao().selectAll().get(0);
        dataBaseService.getCardDao().delete(card);

        user = dataBaseService.getUserDao().getById(user.getId());
        asserts.checkNumber(0, user.getCards().size(), "Cards count");
    }

    @Feature("Select entity")
    @Description("Unique user test")
    @Test
    public void uniqueUserTest() {
        testUtils.logTestStart("Unique user test");

        String login = "Login";
        String anotherLogin = "Login1";
        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User(login, "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user);

        asserts.checkObject(true, dataBaseService.getUserDao().isLoginUnique(anotherLogin), "Login unique");
    }

    @Feature("Select entity")
    @Description("Not unique user test")
    @Test
    public void notUniqueUserTest() {
        testUtils.logTestStart("Not unique user test");

        String login = "Login";
        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User(login, "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user);

        asserts.checkObject(false, dataBaseService.getUserDao().isLoginUnique(login), "Login unique");
    }

    @Feature("Select entity")
    @Description("Select with login test")
    @Test
    public void selectWithLoginTest() {
        testUtils.logTestStart("Select with login test");

        String login = "Login";
        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User(login, "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user);

        asserts.checkNotNull(dataBaseService.getUserDao().select(login), "User");
    }

    @Feature("Select entity")
    @Description("Select with unknown login test")
    @Test
    public void selectWithUnknownLoginTest() {
        testUtils.logTestStart("Select with unknown login test");

        String login = "Login";
        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User user = new User(login, "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(user);

        asserts.checkNull(dataBaseService.getUserDao().select(login + "ert"), "User");
    }
}
