package vezh_bank.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.UserRequestStatus;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRequest;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.persistence.providers.user_request.SelectRequestArgumentsProvider;
import vezh_bank.persistence.providers.user_request.SelectRequestWirhPageArgumentsProvider;

import java.util.List;

public class UserRequestTests extends PersistenceTest {

    @Test
    public void insertTest() {
        testUtils.logTestStart("Insert user request test");
        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);

        List<UserRequest> userRequests = dataBaseService.getUserRequestDao().selectAll();
        checkUserRequestsCount(1, userRequests);
        checkUserRequest(user.getId(), UserRequestStatus.OPEN, data, userRequests.get(0));
    }

    @Test
    public void selectByIdTest() {
        testUtils.logTestStart("Select user request by ID test");

        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);
        userRequest = dataBaseService.getUserRequestDao().getById(userRequest.getId());

        checkUserRequest(user.getId(), UserRequestStatus.OPEN, data, userRequest);
    }

    @Test
    public void updateTest() {
        testUtils.logTestStart("Update user request test");

        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);
        userRequest = dataBaseService.getUserRequestDao().getById(userRequest.getId());

        UserRequestStatus userRequestStatus = UserRequestStatus.IN_PROGRESS;
        userRequest.setStatus(userRequestStatus);
        dataBaseService.getUserRequestDao().update(userRequest);

        userRequest = dataBaseService.getUserRequestDao().getById(userRequest.getId());
        checkUserRequest(user.getId(), userRequestStatus, data, userRequest);
    }

    @Test
    public void deleteTest() {
        testUtils.logTestStart("Delete user request test");

        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);
        userRequest = dataBaseService.getUserRequestDao().getById(userRequest.getId());

        dataBaseService.getUserRequestDao().delete(userRequest);

        checkUserRequestsCount(0, dataBaseService.getUserRequestDao().selectAll());
    }

    @Test
    public void deleteByIdTest() {
        testUtils.logTestStart("Delete user request by ID test");

        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);
        userRequest = dataBaseService.getUserRequestDao().selectAll().get(0);
        userRequest = dataBaseService.getUserRequestDao().getById(userRequest.getId());

        dataBaseService.getUserRequestDao().delete(userRequest.getId());

        checkUserRequestsCount(0, dataBaseService.getUserRequestDao().selectAll());
    }

    @ParameterizedTest
    @ArgumentsSource(SelectRequestArgumentsProvider.class)
    public void selectTest(int user, String creationDate, String status, String data,
                           int expectedUserRequestsCount) {
        testUtils.logTestStart("Select user request with params test");

        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();

        dataBaseService.getUserDao().insert(new User("Test 1", "Password", roles.get(0),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 2", "Password", roles.get(1),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 3", "Password", roles.get(2),
                "data", "config", 3));
        List<User> users = dataBaseService.getUserDao().selectAll();

        UserRequest userRequest1 = new UserRequest(users.get(0), "Request data 1");
        UserRequest userRequest2 = new UserRequest(users.get(1), "Request data 2");
        UserRequest userRequest3 = new UserRequest(users.get(2), "Request data 3");
        UserRequest userRequest4 = new UserRequest(users.get(0), "Request data 4");
        userRequest4.setStatus(UserRequestStatus.IN_PROGRESS);
        UserRequest userRequest5 = new UserRequest(users.get(1), "Request data 5");
        userRequest5.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest6 = new UserRequest(users.get(2), "Request data 6");
        UserRequest userRequest7 = new UserRequest(users.get(0), "Request data 7");
        userRequest7.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest8 = new UserRequest(users.get(1), "Request data 8");

        createUserRequest(userRequest1);
        createUserRequest(userRequest2);
        createUserRequest(userRequest3);
        createUserRequest(userRequest4);
        createUserRequest(userRequest5);
        createUserRequest(userRequest6);
        createUserRequest(userRequest7);
        createUserRequest(userRequest8);

        List<UserRequest> userRequests = dataBaseService.getUserRequestDao().select(
                String.valueOf(users.get(user).getId()), creationDate, status, data);

        checkUserRequestsCount(expectedUserRequestsCount, userRequests);
    }

    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select user request count test");

        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();

        dataBaseService.getUserDao().insert(new User("Test 1", "Password", roles.get(0),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 2", "Password", roles.get(1),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 3", "Password", roles.get(2),
                "data", "config", 3));
        List<User> users = dataBaseService.getUserDao().selectAll();

        UserRequest userRequest1 = new UserRequest(users.get(0), "Request data 1");
        UserRequest userRequest2 = new UserRequest(users.get(1), "Request data 2");
        UserRequest userRequest3 = new UserRequest(users.get(2), "Request data 3");
        UserRequest userRequest4 = new UserRequest(users.get(0), "Request data 4");
        userRequest4.setStatus(UserRequestStatus.IN_PROGRESS);
        UserRequest userRequest5 = new UserRequest(users.get(1), "Request data 5");
        userRequest5.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest6 = new UserRequest(users.get(2), "Request data 6");
        UserRequest userRequest7 = new UserRequest(users.get(0), "Request data 7");
        userRequest7.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest8 = new UserRequest(users.get(1), "Request data 8");

        createUserRequest(userRequest1);
        createUserRequest(userRequest2);
        createUserRequest(userRequest3);
        createUserRequest(userRequest4);
        createUserRequest(userRequest5);
        createUserRequest(userRequest6);
        createUserRequest(userRequest7);
        createUserRequest(userRequest8);

        Assertions.assertEquals(8, dataBaseService.getUserRequestDao().selectCount(),
                "User requests count");
    }

    @ParameterizedTest
    @ArgumentsSource(SelectRequestWirhPageArgumentsProvider.class)
    public void selectWithPageTest(int requiredPage, int rowsOnPage, String userId, String creationDate,
                                   String status, String data,
                                   int expectedUserRequestsCount) {
        testUtils.logTestStart("Select user requests with pages test");

        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();

        dataBaseService.getUserDao().insert(new User("Test 1", "Password", roles.get(0),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 2", "Password", roles.get(1),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 3", "Password", roles.get(2),
                "data", "config", 3));
        List<User> users = dataBaseService.getUserDao().selectAll();

        UserRequest userRequest1 = new UserRequest(users.get(0), "Request data 1");
        UserRequest userRequest2 = new UserRequest(users.get(1), "Request data 2");
        UserRequest userRequest3 = new UserRequest(users.get(2), "Request data 3");
        UserRequest userRequest4 = new UserRequest(users.get(0), "Request data 4");
        userRequest4.setStatus(UserRequestStatus.IN_PROGRESS);
        UserRequest userRequest5 = new UserRequest(users.get(1), "Request data 5");
        userRequest5.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest6 = new UserRequest(users.get(2), "Request data 6");
        UserRequest userRequest7 = new UserRequest(users.get(0), "Request data 7");
        userRequest7.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest8 = new UserRequest(users.get(1), "Request data 8");

        createUserRequest(userRequest1);
        createUserRequest(userRequest2);
        createUserRequest(userRequest3);
        createUserRequest(userRequest4);
        createUserRequest(userRequest5);
        createUserRequest(userRequest6);
        createUserRequest(userRequest7);
        createUserRequest(userRequest8);

        List<UserRequest> userRequests = dataBaseService.getUserRequestDao().select(requiredPage, rowsOnPage,
                userId, creationDate, status, data);

        checkUserRequestsCount(expectedUserRequestsCount, userRequests);
    }

    @ParameterizedTest
    @ArgumentsSource(SelectRequestArgumentsProvider.class)
    public void selectCountTest(String user, String creationDate, String status, String data,
                                int expectedUserRequestsCount) {
        testUtils.logTestStart("Select user requests count with params test");

        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();

        dataBaseService.getUserDao().insert(new User("Test 1", "Password", roles.get(0),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 2", "Password", roles.get(1),
                "data", "config", 3));
        dataBaseService.getUserDao().insert(new User("Test 3", "Password", roles.get(2),
                "data", "config", 3));
        List<User> users = dataBaseService.getUserDao().selectAll();

        UserRequest userRequest1 = new UserRequest(users.get(0), "Request data 1");
        UserRequest userRequest2 = new UserRequest(users.get(1), "Request data 2");
        UserRequest userRequest3 = new UserRequest(users.get(2), "Request data 3");
        UserRequest userRequest4 = new UserRequest(users.get(0), "Request data 4");
        userRequest4.setStatus(UserRequestStatus.IN_PROGRESS);
        UserRequest userRequest5 = new UserRequest(users.get(1), "Request data 5");
        userRequest5.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest6 = new UserRequest(users.get(2), "Request data 6");
        UserRequest userRequest7 = new UserRequest(users.get(0), "Request data 7");
        userRequest7.setStatus(UserRequestStatus.CLOSED);
        UserRequest userRequest8 = new UserRequest(users.get(1), "Request data 8");

        createUserRequest(userRequest1);
        createUserRequest(userRequest2);
        createUserRequest(userRequest3);
        createUserRequest(userRequest4);
        createUserRequest(userRequest5);
        createUserRequest(userRequest6);
        createUserRequest(userRequest7);
        createUserRequest(userRequest8);

        int userRequests = dataBaseService.getUserRequestDao().selectCount(
               users.get(user).getId(), creationDate, status, data); //TODO: fix this shit

        Assertions.assertEquals(expectedUserRequestsCount, userRequests,
                "User requests count");
    }

    //TODO: add test for absent user
}
