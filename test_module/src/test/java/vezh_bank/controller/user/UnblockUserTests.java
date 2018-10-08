package vezh_bank.controller.user;

import core.json.EventData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.controller.user.providers.blocking.RolesArgumentsProvider;
import vezh_bank.enums.EventType;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.entity.User;

import java.util.List;

@Epic("User controller")
@Story("Unblock user")
@Link(name = "Issue", value = "https://vezh-lab.atlassian.net/browse/VB-2", url = "https://vezh-lab.atlassian.net/browse/VB-2")
public class UnblockUserTests extends ControllerTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Unblock user")
    @Description("Block user success test")
    @Test
    public void blockUserSuccess() {
        testUtils.logTestStart("Block user success test");

        int clientId = testUtils.createClient(serviceProvider.getDataBaseService());
        int adminId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        User user = serviceProvider.getDataBaseService().getUserDao().getById(clientId);
        user.setBlocked(true);
        serviceProvider.getDataBaseService().getUserDao().update(user);
        user = serviceProvider.getDataBaseService().getUserDao().getById(clientId);

        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(adminId));

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(clientId) + Urls.UNBLOCK, params);

        httpAsserts.checkResponseCode(200, response);

        asserts.checkFalse(serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).isBlocked(),
                "User block");

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_UNBLOCKED, new EventData(EventDescriptions.userHasBeenUnblocked(user.getLogin())),
                events.get(0));
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User access validation")
    @Description("Unblock user without access test")
    @ArgumentsSource(RolesArgumentsProvider.class)
    @ParameterizedTest
    public void blockWithoutAccess(Role role) {
        testUtils.logTestStart("Unblock user without access test");

        int clientId = testUtils.createClient(serviceProvider.getDataBaseService());
        int adminId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(role.toString()));
        User user = serviceProvider.getDataBaseService().getUserDao().getById(clientId);

        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(adminId));

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(clientId) + Urls.UNBLOCK, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.ACCESS_DENIED, response);

        asserts.checkFalse(serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).isBlocked(),
                "User block");

        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(targetId) + Urls.UNBLOCK, params);

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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(targetId) + Urls.UNBLOCK, params);

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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(targetId) + Urls.UNBLOCK, params);

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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/a" + String.valueOf(targetId) + Urls.UNBLOCK, params);

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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/" + String.valueOf(targetId) + Urls.UNBLOCK, params);

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

        MockHttpServletResponse response = httpPut(Urls.USERS + "/666" + Urls.UNBLOCK, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist("666"), response);
    }
}
