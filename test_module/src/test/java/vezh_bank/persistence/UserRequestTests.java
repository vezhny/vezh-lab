package vezh_bank.persistence;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
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

@Story("User request persistence")
@Link(url = "https://github.com/vezhny/vezh-lab/issues/5")
public class UserRequestTests extends PersistenceTest {

    @Description("Insert user request test")
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
        userRequestAsserts.checkUserRequestsCount(1, userRequests);
        userRequestAsserts.checkUserRequest(user.getId(), UserRequestStatus.OPEN, data, userRequests.get(0));
    }

    @Description("Select user request by ID test")
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

        userRequestAsserts.checkUserRequest(user.getId(), UserRequestStatus.OPEN, data, userRequest);
    }

    @Description("Update user request test")
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
        userRequestAsserts.checkUserRequest(user.getId(), userRequestStatus, data, userRequest);
    }

    @Description("Delete user request test")
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

        userRequestAsserts.checkUserRequestsCount(0, dataBaseService.getUserRequestDao().selectAll());
    }

    @Description("Delete user request by ID test")
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

        userRequestAsserts.checkUserRequestsCount(0, dataBaseService.getUserRequestDao().selectAll());
    }

    @Description("Select user request with params test. User: {0}, status: {2}, data: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectRequestArgumentsProvider.class)
    public void selectTest(String user, String creationDate, String status, String data,
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
                getUserId(user, users), creationDate, status, data);

        userRequestAsserts.checkUserRequestsCount(expectedUserRequestsCount, userRequests);
    }

    @Description("Select user request count test")
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

        asserts.checkObject(8, dataBaseService.getUserRequestDao().selectCount(),
                "User requests count");
    }

    @Description("Select user request with pages test. Required page: {0}, rows on page: {1}, user: {2}, status: {4}, " +
            "data: {5}")
    @ParameterizedTest
    @ArgumentsSource(SelectRequestWirhPageArgumentsProvider.class)
    public void selectWithPageTest(int requiredPage, int rowsOnPage, String user, String creationDate,
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
                getUserId(user, users), creationDate, status, data);

        userRequestAsserts.checkUserRequestsCount(expectedUserRequestsCount, userRequests);
    }

    @Description("Select user requests count with params test. User: {0}, status: {2}, data: {3}")
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

        int userRequests = dataBaseService.getUserRequestDao().selectCount(getUserId(user, users),
                creationDate, status, data);

        asserts.checkObject(expectedUserRequestsCount, userRequests,
                "User requests count");
    }

    @Description("Absent user test")
    @Test
    public void absentUserTest() {
        testUtils.logTestStart("Absent user test");

        UserRole userRole = dataBaseService.getRoleDao().getById(1);

        User user = new User("test", "test", userRole,
                "Test data", "Config", 3);
        dataBaseService.getUserDao().insert(user);
        user = dataBaseService.getUserDao().selectAll().get(0);

        String data = "data";
        UserRequest userRequest = new UserRequest(user, data);

        dataBaseService.getUserRequestDao().insert(userRequest);
        dataBaseService.getUserDao().delete(user);

        List<UserRequest> userRequests = dataBaseService.getUserRequestDao().selectAll();
        userRequestAsserts.checkUserRequestsCount(1, userRequests);
        asserts.checkNull(userRequests.get(0).getUser(), "user");
        asserts.checkObject(UserRequestStatus.OPEN.toString(), userRequests.get(0).getStatus(), "request status");
        asserts.checkObject(data, userRequests.get(0).getData(), "user request data");
    }

    @Description("Select user requests with user iD test")
    @Test
    public void selectWithUserIdTest() {
        testUtils.logTestStart("Select user requests with user iD test");

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

        createUserRequest(userRequest1);
        createUserRequest(userRequest2);
        createUserRequest(userRequest3);
        createUserRequest(userRequest4);

        List<UserRequest> userRequests = dataBaseService.getUserRequestDao().select(users.get(0).getId());

        userRequestAsserts.checkUserRequestsCount(2, userRequests);
    }

    @Step
    private String getUserId(String user, List<User> users) {
        if (!user.isEmpty()) {
            return String.valueOf(users.get(Integer.parseInt(user)).getId());
        }
        return user;
    }
}
