package vezh_bank.controller.user;

import core.dto.UserDTO;
import core.json.UserAddress;
import core.json.UserData;
import core.json.Users;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.controller.user.providers.get.GetUsersArgumentsProvider;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.UserRole;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Epic("User controller")
@Story("Get users")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/18", url = "https://github.com/vezhny/vezh-lab/issues/18")
public class GetUsersTests extends ControllerTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Get users")
    @Description("Get all users test")
    @Test
    public void getAllUsers() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get all users test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        testUtils.createClient(serviceProvider.getDataBaseService());
        testUtils.createClient(serviceProvider.getDataBaseService());
        testUtils.createClient(serviceProvider.getDataBaseService());
        testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.EMPLOYEE.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(200, response.getStatus());

        Users users = gson.fromJson(response.getContentAsString(), Users.class);
        userAsserts.checkNumberOfUsers(7, users.getUsers().size());
        httpAsserts.checkCurrentPage(1, response);
        httpAsserts.checkPagesCount(1, response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User id validation")
    @Description("Get users when user id absent")
    @Test
    public void getUsersAbsentUserId() {
        testUtils.logTestStart("Get users when user id absent");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User id validation")
    @Description("Get users when user id can't be a number")
    @Test
    public void getUsersUserIdCanNotBeNumber() {
        testUtils.logTestStart("Get users when user id can't be a number");

        String userId = "x";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User id validation")
    @Description("Get users when user doesn't exist")
    @Test
    public void getUsersUserDoesNotExist() {
        testUtils.logTestStart("Get users when user doesn't exist");

        String userId = "999";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(String.format(ExceptionMessages.USER_DOES_NOT_EXIST, userId), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User id validation")
    @Description("Get users when user is client")
    @Test
    public void getUsersUserIsClient() {
        testUtils.logTestStart("Get users when user is client");

        String userId = String.valueOf(testUtils.createClient(serviceProvider.getDataBaseService()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.ACCESS_DENIED, response);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Get users")
    @Description("Get users parametrized")
    @ArgumentsSource(GetUsersArgumentsProvider.class)
    @ParameterizedTest
    public void getUsers(int requiredPage, String login, String role, String blocked, String data,
                         int expectedUsersCount, int expectedCurrentPage, int expectedNumberOfPages) throws UnsupportedEncodingException {
        testUtils.logTestStart("Get users parametrized test");
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();

        UserAddress address1 = new UserAddress("country1", "region1", "city1", "street1", "23", "111");
        UserAddress address2 = new UserAddress("country2", "region2", "city2", "street2", "24", "113");
        UserAddress address3 = new UserAddress("country3", "region3", "city3", "street3", "25", "115");

        UserData userData1 = new UserData("firstName1", "middleName1", "patronymic1", "10.10.1999",
                address1, "+36985215", "test1@test.com");
        UserData userData2 = new UserData("firstName2", "middleName2", "patronymic2", "10.08.1999",
                address2, "+36985217", "test2@test.com");
        UserData userData3 = new UserData("firstName3", "middleName3", "patronymic3", "10.04.1999",
                address3, "+36985219", "test3@test.com");

        serviceProvider.getUserService().addUser(new UserDTO("login1", "password", userRoles.get(0), userData1));
        serviceProvider.getUserService().addUser(new UserDTO("login2", "password", userRoles.get(1), userData2));
        serviceProvider.getUserService().addUser(new UserDTO("login3", "password", userRoles.get(2), userData3));
        serviceProvider.getUserService().addUser(new UserDTO("login4", "password", userRoles.get(0), userData1));
        serviceProvider.getUserService().addUser(new UserDTO("login5", "password", userRoles.get(1), userData2));
        serviceProvider.getUserService().addUser(new UserDTO("login6", "password", userRoles.get(2), userData3));
        serviceProvider.getUserService().addUser(new UserDTO("login7", "password", userRoles.get(0), userData1));
        serviceProvider.getUserService().addUser(new UserDTO("login8", "password", userRoles.get(1), userData2));
        serviceProvider.getUserService().addUser(new UserDTO("login9", "password", userRoles.get(2), userData3));
        serviceProvider.getUserService().addUser(new UserDTO("login10", "password", userRoles.get(0), userData1));
        serviceProvider.getUserService().addUser(new UserDTO("login11", "password", userRoles.get(1), userData2));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));
        params.set(RequestParams.REQUIRED_PAGE, String.valueOf(requiredPage));
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.ROLE, role);
        params.set(RequestParams.BLOCKED, blocked);
        params.set(RequestParams.USER_DATA, data);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(200, response.getStatus());

        Users users = gson.fromJson(response.getContentAsString(), Users.class);
        userAsserts.checkNumberOfUsers(expectedUsersCount, users.getUsers().size());
        httpAsserts.checkCurrentPage(expectedCurrentPage, response);
        httpAsserts.checkPagesCount(expectedNumberOfPages, response);
    }
}
