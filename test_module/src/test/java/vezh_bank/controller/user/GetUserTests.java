package vezh_bank.controller.user;

import core.dto.UserDTO;
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
import vezh_bank.controller.user.providers.get.GetUserByHimselfRolesProvider;
import vezh_bank.controller.user.providers.get.GetUserWithoutAccessArgumentsProvider;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;

import java.io.UnsupportedEncodingException;

@Epic("User controller")
@Story("Get user")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/35", url = "https://github.com/vezhny/vezh-lab/issues/35")
public class GetUserTests extends ControllerTest {

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Get user")
    @Description("Get user by admin")
    @Test
    public void getUserByAdmin() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get user by admin test");

        int clientId = testUtils.createClient(serviceProvider.getDataBaseService());
        int adminId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(adminId));

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(clientId), params);

        httpAsserts.checkResponseCode(200, response);

        UserDTO userDTO = gson.fromJson(response.getContentAsString(), UserDTO.class);
        asserts.checkNumber(clientId, userDTO.getId(), "User id");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Get user")
    @Description("Get user by himself")
    @ArgumentsSource(GetUserByHimselfRolesProvider.class)
    @ParameterizedTest
    public void getUserByHimself(Role role) throws UnsupportedEncodingException {
        testUtils.logTestStart("Get user by himself test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(role.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(userId), params);

        httpAsserts.checkResponseCode(200, response);

        UserDTO userDTO = gson.fromJson(response.getContentAsString(), UserDTO.class);
        asserts.checkNumber(userId, userDTO.getId(), "User id");
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("Get user")
    @Description("Attempt to get user without access")
    @ArgumentsSource(GetUserWithoutAccessArgumentsProvider.class)
    @ParameterizedTest
    public void getUserWithoutAccess(Role userRole, Role targetRole, int expectedResponseCode,
                                     String expectedExceptionMessage) {
        testUtils.logTestStart("Attempt to get user without access test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(userRole.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(targetRole.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(expectedResponseCode, response);
        httpAsserts.checkExceptionMessage(expectedExceptionMessage, response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID is absent")
    @Test
    public void userIdIsAbsent() {
        testUtils.logTestStart("User ID is absent test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID is empty")
    @Test
    public void userIdIsEmpty() {
        testUtils.logTestStart("User ID is empty test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, "");

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID can't be a number")
    @Test
    public void userIdCanNotBeANumber() {
        testUtils.logTestStart("User ID can't be a number test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, "a");

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("User Id validation")
    @Description("Target ID can't be a number")
    @Test
    public void targetIdCanNotBeANumber() {
        testUtils.logTestStart("Target ID can't be a number test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.USERS + "/a" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.TARGET_ID), response);
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID doesn't exist")
    @Test
    public void userIdDoesNotExist() {
        testUtils.logTestStart("User ID doesn't exist test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, "666");

        MockHttpServletResponse response = httpGet(Urls.USERS + "/" + String.valueOf(targetId), params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist("666"), response);
    }

    @Severity(SeverityLevel.BLOCKER)
    @Feature("User Id validation")
    @Description("Target ID doesn't exist")
    @Test
    public void targetIdDoesNotExist() {
        testUtils.logTestStart("Target ID doesn't exist test");

        int userId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int targetId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpGet(Urls.USERS + "/666", params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist("666"), response);
    }
}
