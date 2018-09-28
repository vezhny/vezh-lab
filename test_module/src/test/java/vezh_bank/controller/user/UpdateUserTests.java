package vezh_bank.controller.user;

import com.google.gson.Gson;
import core.dto.UserDTO;
import core.json.EventData;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
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
import vezh_bank.controller.user.providers.delete.ClientAndEmployeeArguments;
import vezh_bank.controller.user.providers.update.OneRoleArgumentsProvider;
import vezh_bank.controller.user.providers.update.RequestParamsArgumentsProvider;
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
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/29", url = "https://github.com/vezhny/vezh-lab/issues/29")
public class UpdateUserTests extends ControllerTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Update user success")
    @Description("Update user success test")
    @Test
    public void updateUser() {
        testUtils.logTestStart("Update user success test");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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
        UserDTO updatedUser = new UserDTO(client, serviceProvider.getDataBaseService().getRoleDao().get(client.getRole()));
        updatedUser.setPassword(newPassword);
        updatedUser.setData(updatedData);
        updatedUser.setConfig(updatedConfig);

        userAsserts.checkUser(updatedUser, serviceProvider.getDataBaseService().getUserDao().getById(client.getId()));

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_UPDATE, new EventData(EventDescriptions.userHasBeenUpdatedBy(admin.getLogin(),
                admin.getRole(), client.getLogin(), client.getRole())), events.get(0));
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID is absent")
    @Test
    public void userIdIsAbsent() {
        testUtils.logTestStart("User ID is absent");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("Updating user ID is absent")
    @Test
    public void updatingUserIdIsAbsent() {
        testUtils.logTestStart("Updating user ID is absent");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.missingParameter(RequestParams.UPDATING_USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID is empty")
    @Test
    public void userIdIsEmpty() {
        testUtils.logTestStart("User ID is empty");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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
        params.set(RequestParams.USER_ID, "");
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("Updating user ID is empty")
    @Test
    public void updatingUserIdIsEmpty() {
        testUtils.logTestStart("Updating user ID is empty");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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
        params.set(RequestParams.UPDATING_USER_ID, "");
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.UPDATING_USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID can't be a number")
    @Test
    public void userIdCantBeANumber() {
        testUtils.logTestStart("User ID can't be a number");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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
        params.set(RequestParams.USER_ID, "t");
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("Updating user ID can't be a number")
    @Test
    public void updatingUserIdCantBeANumber() {
        testUtils.logTestStart("Updating user ID can't be a number");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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
        params.set(RequestParams.UPDATING_USER_ID, "t");
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.invalidParameter(RequestParams.UPDATING_USER_ID), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("User ID doesn't exist")
    @Test
    public void userIdDoesNotExist() {
        testUtils.logTestStart("User ID doesn't exist");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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

        String unexistingId = "1";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, unexistingId);
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist(unexistingId), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User Id validation")
    @Description("Updating user ID doesn't exist")
    @Test
    public void updatingUserIdDoesNotExist() {
        testUtils.logTestStart("Updating user ID doesn't exist");

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);

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

        String unexistingId = "1";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(admin.getId()));
        params.set(RequestParams.UPDATING_USER_ID, unexistingId);
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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.userDoesNotExist(unexistingId), response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User access validation")
    @Description("User Ids are same")
    @ArgumentsSource(OneRoleArgumentsProvider.class)
    @ParameterizedTest
    public void userIdsAreSame(Role updaterRole) {
        testUtils.logTestStart("User IDs are same");

        User admin = createUpdater(updaterRole);

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
        String birthDate = new Gson().fromJson(admin.getData(), UserData.class).getBirthDate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(admin.getId()));
        params.set(RequestParams.UPDATING_USER_ID, String.valueOf(admin.getId()));
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
        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(1, serviceProvider.getDataBaseService().getEventDao().selectCount());

        UserAddress updatedAddress = new UserAddress(newCountry, newRegion, newCity, newStreet, newHouse, newHousePart, newRoom);
        UserData updatedData = new UserData(newFirstName, newMiddleName, newPatronymic, birthDate, updatedAddress, newContactNumber, newEmail);
        UserConfig updatedConfig = new UserConfig(stringToInt(newCardsOnPage), stringToInt(newCurrenciesOnPage),
                stringToInt(newEventsOnPage), stringToInt(newPaymentsOnPage), stringToInt(newTransactionsOnPage),
                stringToInt(newUserRequestsOnPage), stringToInt(newUsersOnPage));
        UserDTO updatedUser = new UserDTO(admin, serviceProvider.getDataBaseService().getRoleDao().get(admin.getRole()));
        updatedUser.setPassword(newPassword);
        updatedUser.setData(updatedData);
        updatedUser.setConfig(updatedConfig);

        userAsserts.checkUser(updatedUser, serviceProvider.getDataBaseService().getUserDao().getById(admin.getId()));

        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_UPDATE, new EventData(EventDescriptions.userHasBeenUpdatedBy(admin.getLogin(),
                admin.getRole(), admin.getLogin(), admin.getRole())), events.get(0));
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User access validation")
    @Description("User doesn't has access")
    @ArgumentsSource(ClientAndEmployeeArguments.class)
    @ParameterizedTest
    public void userDoesNotHaveAccess(Role role) {
        testUtils.logTestStart("User doesn't has access");

        User admin = createUpdater(role);
        User client = createVictim(Role.CLIENT);

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

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.ACCESS_DENIED, response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("Update request params validation")
    @Description("Request params parametrized test")
    @ArgumentsSource(RequestParamsArgumentsProvider.class)
    @ParameterizedTest
    public void requestParamsTest(String description, String password, String country, String region, String city,
                                  String street, String house, String housePart, String room, String firstName,
                                  String middleName, String patronymic, String contactNumber, String email,
                                  String cardsOnPage, String currenciesOnPage, String eventsOnPage,
                                  String paymentsOnPage, String transactionsOnPage, String usersOnPage,
                                  String userRequestsOnPage, String expectedErrorMessage) {
        testUtils.logTestStart(description);

        User admin = createUpdater(Role.ADMIN);
        User client = createVictim(Role.CLIENT);
        String birthDate = new Gson().fromJson(client.getData(), UserData.class).getBirthDate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, String.valueOf(admin.getId()));
        params.set(RequestParams.UPDATING_USER_ID, String.valueOf(client.getId()));
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.COUNTRY, country);
        params.set(RequestParams.REGION, region);
        params.set(RequestParams.CITY, city);
        params.set(RequestParams.STREET, street);
        params.set(RequestParams.HOUSE, house);
        params.set(RequestParams.HOUSE_PART, housePart);
        params.set(RequestParams.ROOM, room);
        params.set(RequestParams.FIRST_NAME, firstName);
        params.set(RequestParams.MIDDLE_NAME, middleName);
        params.set(RequestParams.PATRONYMIC, patronymic);
        params.set(RequestParams.BIRTH_DATE, birthDate);
        params.set(RequestParams.CONTACT_NUMBER, contactNumber);
        params.set(RequestParams.EMAIL, email);
        params.set(RequestParams.CARDS_ON_PAGE, cardsOnPage);
        params.set(RequestParams.CURRENCIES_ON_PAGE, currenciesOnPage);
        params.set(RequestParams.EVENTS_ON_PAGE, eventsOnPage);
        params.set(RequestParams.PAYMENTS_ON_PAGE, paymentsOnPage);
        params.set(RequestParams.TRANSACTIONS_ON_PAGE, transactionsOnPage);
        params.set(RequestParams.USERS_ON_PAGE, usersOnPage);
        params.set(RequestParams.USER_REQUESTS_ON_PAGE, userRequestsOnPage);

        MockHttpServletResponse response = httpPut(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(expectedErrorMessage, response);
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    private User createUpdater(Role role) {
        UserRole adminRole = serviceProvider.getDataBaseService().getRoleDao().get(role.toString());
        String adminLogin = "admin";

        String adminPassword = "password";
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        User admin = testUtils.createUser(new UserDTO(adminLogin, adminPassword,
                adminRole, userData), serviceProvider);

        return admin;
    }

    private User createVictim(Role role) {
        UserRole clientRole = serviceProvider.getDataBaseService().getRoleDao().get(role.toString());
        String clientLogin = "client";

        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        String clientPassword = "passwordec";
        UserAddress addressClient = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userDataClient = new UserData("Test", "Test", "Test",
                "10.11.1992", addressClient, "+375293956223", "vezhny@gmail.com");
        User client = testUtils.createUser(new UserDTO(clientLogin, clientPassword,
                clientRole, userDataClient), serviceProvider);

        return client;
    }
}
