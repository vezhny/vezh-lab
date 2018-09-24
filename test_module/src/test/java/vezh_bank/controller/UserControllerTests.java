package vezh_bank.controller;

import core.dto.UserRoleDTO;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.*;
import vezh_bank.controller.providers.RegisterUserFailArgumentsProvider;
import vezh_bank.controller.providers.RegisterUserSuccessAgrumentsProvider;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

@Feature("User controller")
public class UserControllerTests extends ControllerTest {

    @Description("Register user success test")
    @Test
    public void registerUserSuccess() {
        testUtils.logTestStart("Register user success test");

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();
        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(userRoles.get(0));
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.ROLE, roleDTO.getName());
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, address.getHouse());
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());
        params.set(RequestParams.EMAIL, userData.getEmail());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        checkNumberOfUsers(1, users.size());

        checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success with house part test")
    @Test
    public void registerUserSuccessWithHousePart() {
        testUtils.logTestStart("Register user success with house part test");

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();
        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(userRoles.get(0));
        String house = "30";
        String housePart = "2";
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", house, housePart, "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.ROLE, roleDTO.getName());
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, house);
        params.set(RequestParams.HOUSE_PART, housePart);
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());
        params.set(RequestParams.EMAIL, userData.getEmail());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        checkNumberOfUsers(1, users.size());

        checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success without email test")
    @Test
    public void registerUserSuccessWithoutEmail() {
        testUtils.logTestStart("Register user success without email test");

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();
        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(userRoles.get(0));
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", null);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.ROLE, roleDTO.getName());
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, address.getHouse());
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        checkNumberOfUsers(1, users.size());

        checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("{0}")
    @ParameterizedTest
    @ArgumentsSource(RegisterUserFailArgumentsProvider.class)
    public void registerUserFail(String description, String login, String password, String role, String country,
                                 String region, String city, String street, String house, String room,
                                 String firstName, String middleName, String patronymic, String birthDate,
                                 String contactNumber, String email, String expectedErrorMessage) {

        testUtils.logTestStart(description);

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        UserAddress address = new UserAddress(country, region, city,
                street, house, room);
        UserData userData = new UserData(firstName, middleName, patronymic,
                birthDate, address, contactNumber, email);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.ROLE, role);
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, address.getHouse());
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());
        params.set(RequestParams.EMAIL, userData.getEmail());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(400, response.getStatus());

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());
        Assertions.assertEquals(expectedErrorMessage, response.getHeader(Headers.ERROR_MESSAGE),
                "Exception message");
    }

    @Description("{0}")
    @ParameterizedTest
    @ArgumentsSource(RegisterUserSuccessAgrumentsProvider.class)
    public void registerUserSuccess(String description, String login, String role, String firstName) {
        testUtils.logTestStart(description);

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData(firstName, "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, "password");
        params.set(RequestParams.ROLE, role);
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, address.getHouse());
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());
        params.set(RequestParams.EMAIL, userData.getEmail());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(200, response.getStatus());

        checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Description("Login in non-unique test")
    @Test
    public void loginIsNonUnique() {
        testUtils.logTestStart("Login in non-unique test");

        checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();
        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(userRoles.get(0));
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, password);
        params.set(RequestParams.ROLE, roleDTO.getName());
        params.set(RequestParams.COUNTRY, address.getCountry());
        params.set(RequestParams.REGION, address.getRegion());
        params.set(RequestParams.CITY, address.getCity());
        params.set(RequestParams.STREET, address.getStreet());
        params.set(RequestParams.HOUSE, address.getHouse());
        params.set(RequestParams.ROOM, address.getRoom());
        params.set(RequestParams.FIRST_NAME, userData.getFirstName());
        params.set(RequestParams.MIDDLE_NAME, userData.getMiddleName());
        params.set(RequestParams.PATRONYMIC, userData.getPatronymic());
        params.set(RequestParams.BIRTH_DATE, userData.getBirthDate());
        params.set(RequestParams.CONTACT_NUMBER, userData.getContactNumber());
        params.set(RequestParams.EMAIL, userData.getEmail());

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        checkResponseCode(200, response.getStatus());

        response = httpPost(Urls.USERS, params);

        checkResponseCode(400, response.getStatus());
        Assertions.assertEquals(String.format(ExceptionMessages.USER_WITH_LOGIN_IS_ALREADY_REGISTERED, login),
                response.getHeader(Headers.ERROR_MESSAGE),
                "Exception message");
        checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }
}
