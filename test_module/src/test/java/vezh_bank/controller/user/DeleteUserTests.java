package vezh_bank.controller.user;

import core.dto.UserDTO;
import core.json.EventData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
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
import vezh_bank.controller.user.providers.delete.ClientAndEmployeeArguments;
import vezh_bank.controller.user.providers.delete.RolesArgumentsProvider;
import vezh_bank.enums.EventType;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.entity.User;

import java.util.List;

@Epic("User controller")
@Story("Delete users")
public class DeleteUserTests extends ControllerTest {

    @Description("Delete user success")
    @ArgumentsSource(RolesArgumentsProvider.class)
    @ParameterizedTest
    public void deleteUserSuccess(String victimRole) {
        testUtils.logTestStart("Delete user success test");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int victimId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(victimRole));
        User victimUser = serviceProvider.getDataBaseService().getUserDao().getById(victimId);

        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        userAsserts.checkNumberOfUsers(1, users.size());

        User expectedUserEntity = serviceProvider.getDataBaseService().getUserDao().getById(deleterId);
        UserDTO expectedUser = new UserDTO(expectedUserEntity, serviceProvider.getDataBaseService().getRoleDao().get(expectedUserEntity.getRole()));
        userAsserts.checkUser(expectedUser, users.get(0));

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_DELETE,
                new EventData(EventDescriptions.userDeletedUser(expectedUser.getLogin(), Role.ADMIN.toString(),
                        victimUser.getLogin(), victimRole)), events.get(0));
    }

    @Description("{0} tries to delete user")
    @ParameterizedTest
    @ArgumentsSource(ClientAndEmployeeArguments.class)
    public void userWithoutAccessTriesToDeleteUser(String accessorRole) {
        testUtils.logTestStart(accessorRole + " tries to delete user");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(accessorRole));
        int victimId = testUtils.createClient(serviceProvider.getDataBaseService());

        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.ACCESS_DENIED, response);
    }

    @Description("Delete user when user id absent")
    @Test
    public void deleteUsersAbsentUserId() {
        testUtils.logTestStart("Delete user when user id absent");

        int victimId = testUtils.createClient(serviceProvider.getDataBaseService());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.USER_ID), response);
    }

    @Description("Delete user when victim's user id absent")
    @Test
    public void deleteUsersAbsentVictimUserId() {
        testUtils.logTestStart("Delete user when victim's user id absent");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.DELETING_USER_ID), response);
    }

    @Description("Delete user when user id can't be a number")
    @Test
    public void deleteUsersUserIdCanNotBeNumber() {
        testUtils.logTestStart("Delete user when user id can't be a number");

        int victimId = testUtils.createClient(serviceProvider.getDataBaseService());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, "t");
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
    }

    @Description("Delete user when victim's user id can't be a number")
    @Test
    public void deleteUsersVictimUserIdCanNotBeNumber() {
        testUtils.logTestStart("Delete user when victim's user id can't be a number");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, "h");

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.DELETING_USER_ID), response);
    }

    @Description("Delete user when user doesn't exist")
    @Test
    public void deleteUsersUserDoesNotExist() {
        testUtils.logTestStart("Delete user when user doesn't exist");

        int victimId = testUtils.createClient(serviceProvider.getDataBaseService());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        String deleterId = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, deleterId);
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist(deleterId), response);
    }

    @Description("Delete user when victim doesn't exist")
    @Test
    public void deleteUsersVictimDoesNotExist() {
        testUtils.logTestStart("Delete user when victim doesn't exist");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        String victimId = String.valueOf(System.currentTimeMillis());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, victimId);

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist(victimId), response);
    }

    @Description("Delete user when victim is the same")
    @Test
    public void deleteUsersVictimIdIsTheSame() {
        testUtils.logTestStart("Delete user when victim is the same");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(deleterId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.YOU_CAN_NOT_DELETE_YOURSELF, response);
    }

    @Description("Delete user when victim has cards")
    @Test
    public void deleteUsersVictimHasCards() {
        testUtils.logTestStart("Delete user when victim has cards");

        int deleterId = testUtils.createUser(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString()));
        int victimId = testUtils.createClient(serviceProvider.getDataBaseService());
        User victim = serviceProvider.getDataBaseService().getUserDao().getById(victimId);

        int currency = testUtils.createCurrency(serviceProvider.getDataBaseService(), 643, "RUB");
        testUtils.createCard(serviceProvider.getDataBaseService(),
                serviceProvider.getDataBaseService().getUserDao().getById(victimId),
                serviceProvider.getDataBaseService().getCurrencyDao().getById(currency));

        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(deleterId));
        params.set(RequestParams.DELETING_USER_ID, String.valueOf(victimId));

        MockHttpServletResponse response = httpDelete(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
        httpAsserts.checkExceptionMessage(ExceptionMessages.userHasGotCards(victim.getLogin(), 1), response);
    }
}
