package vezh_bank.controller.user;

import com.google.gson.Gson;
import core.dto.UserDTO;
import core.json.EventData;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.enums.EventType;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

import static vezh_bank.util.TypeConverter.stringToInt;

@Epic("Controller tests")
@Story("Update user")
public class UpdateUserTests extends ControllerTest {

    @Feature("Update user success") // TODO: experimental
    @Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/29", url = "https://github.com/vezhny/vezh-lab/issues/29")
    @Description("Update user success test")
    @Test
    public void updateUser() {
        testUtils.logTestStart("Update user success test");

        UserRole adminRole = serviceProvider.getDataBaseService().getRoleDao().get(Role.ADMIN.toString());
        UserRole clientRole = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        String adminLogin = "admin";
        String clientLogin = "client";

        String adminPassword = "password";
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        String clientPassword = "passwordec";
        UserAddress addressClient = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userDataClient = new UserData("Test", "Test", "Test",
                "10.11.1992", addressClient, "+375293956223", "vezhny@gmail.com");

        User admin = testUtils.createUser(new UserDTO(adminLogin, adminPassword,
                adminRole, userData), serviceProvider);
        User client = testUtils.createUser(new UserDTO(clientLogin, clientPassword,
                clientRole, userDataClient), serviceProvider);

        String newPassword = "dictature!";
        String newCountry = "Russia";
        String newRegion = "Moscow";
        String newCity = "Moscow";
        String newStreet = "New Arbat";
        String newHouse = "1";
        String newHousePart = "1";
        String newRoom = "1";
        String newFirstName = "Vladimir";
        String newMiddleName = "Putin";
        String newPatronymic = "Vladimirovich";
        String newContactNumber = "+375441110010";
        String newEmail = "fuckUSA@creml.ru";
        String newCardsOnPage = "50";
        String newCurrenciesOnPage = "20";
        String newEventsOnPage = "60";
        String newPaymentsOnPage = "30";
        String newTransactionsOnPage = "40";
        String newUsersOnPage = "100";
        String newUserRequestsOnPage = "70";
        String birthDate = new Gson().fromJson(client.getData(), UserData.class).getBirthDate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(admin.getId()));
        params.set(RequestParams.UPDATING_USER_ID, String.valueOf(client.getId()));
        params.set(RequestParams.PASSWORD, newPassword);
        params.set(RequestParams.COUNTRY, newCountry);
        params.set(RequestParams.REGION, newRegion);
        params.set(RequestParams.CITY, newCity);
        params.set(RequestParams.STREET, newStreet);
        params.set(RequestParams.HOUSE, newHouse);
        params.set(RequestParams.HOUSE_PART, newHousePart);
        params.set(RequestParams.ROOM, newRoom);
        params.set(RequestParams.FIRST_NAME, newFirstName);
        params.set(RequestParams.MIDDLE_NAME, newMiddleName);
        params.set(RequestParams.PATRONYMIC, newPatronymic);
        params.set(RequestParams.BIRTH_DATE, birthDate);
        params.set(RequestParams.CONTACT_NUMBER, newContactNumber);
        params.set(RequestParams.EMAIL, newEmail);
        params.set(RequestParams.CARDS_ON_PAGE, newCardsOnPage);
        params.set(RequestParams.CURRENCIES_ON_PAGE, newCurrenciesOnPage);
        params.set(RequestParams.EVENTS_ON_PAGE, newEventsOnPage);
        params.set(RequestParams.PAYMENTS_ON_PAGE, newPaymentsOnPage);
        params.set(RequestParams.TRANSACTIONS_ON_PAGE, newTransactionsOnPage);
        params.set(RequestParams.USERS_ON_PAGE, newUsersOnPage);
        params.set(RequestParams.USER_REQUESTS_ON_PAGE, newUserRequestsOnPage);

        MockHttpServletResponse response = httpPut(Urls.USERS, params);

        httpAsserts.checkResponseCode(200, response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());

        UserAddress updatedAddress = new UserAddress(newCountry, newRegion, newCity, newStreet, newHouse, newHousePart, newRoom);
        UserData updatedData = new UserData(newFirstName, newMiddleName, newPatronymic, birthDate, updatedAddress, newContactNumber, newEmail);
        UserConfig updatedConfig = new UserConfig(stringToInt(newCardsOnPage), stringToInt(newCurrenciesOnPage),
                stringToInt(newEventsOnPage), stringToInt(newPaymentsOnPage), stringToInt(newTransactionsOnPage),
                stringToInt(newUserRequestsOnPage), stringToInt(newUsersOnPage));
        UserDTO updatedUser = new UserDTO(client, clientRole);
        updatedUser.setPassword(newPassword);
        updatedUser.setData(updatedData);
        updatedUser.setConfig(updatedConfig);

        userAsserts.checkUser(updatedUser, serviceProvider.getDataBaseService().getUserDao().getById(client.getId()));

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_UPDATE, new EventData(EventDescriptions.userHasBeenUpdatedBy(adminLogin,
                adminRole.getName(), clientLogin, clientRole.getName())), events.get(0));
    }
}
