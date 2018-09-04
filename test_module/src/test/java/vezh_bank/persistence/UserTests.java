package vezh_bank.persistence;

import org.junit.jupiter.api.Assertions;
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

public class UserTests extends PersistenceTest {

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
        checkUsersCount(1, users);
        checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, users.get(0));
    }

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
        checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, user);
    }

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
        checkUsersCount(1, users);
        checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, newData, users.get(0));
    }

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
        checkUsersCount(0, dataBaseService.getUserDao().selectAll());
    }

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
        checkUsersCount(0, dataBaseService.getUserDao().selectAll());
    }

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

        Assertions.assertEquals(1, dataBaseService.getUserDao().selectCount(),
                "Number of users");
    }

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
        checkUsersCount(expectedUsersCount, users);
    }

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
        checkUsersCount(expectedUsersCount, users);
    }

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
        Assertions.assertEquals(expectedUsersCount, users, "Number of users");
    }

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
        Assertions.assertEquals(1, user.getUserRequests().size(), "User requests count");
        checkUserRequest(user.getId(), UserRequestStatus.OPEN, userRequest.getData(), user.getUserRequests().get(0));
    }

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
        Assertions.assertEquals(0, user.getUserRequests().size(), "User requests count");
    }

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
        Assertions.assertEquals(1, user.getCards().size(), "Cards count");
        checkCard(card.getPan(), user, card.getCvc(), card.getExpiry(), cardCurrency, CardStatus.ACTIVE, card.getAmount(),
                user.getCards().get(0));
    }

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
        Assertions.assertEquals(0, user.getCards().size(), "Cards count");
    }
}
