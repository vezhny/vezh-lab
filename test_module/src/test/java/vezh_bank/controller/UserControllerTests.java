package vezh_bank.controller;

import core.dto.UserRoleDTO;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.constants.Urls;
import vezh_bank.constants.UserDefault;
import vezh_bank.controller.providers.users.registration.RegisterUserFailArgumentsProvider;
import vezh_bank.controller.providers.users.registration.RegisterUserSuccessAgrumentsProvider;
import vezh_bank.controller.providers.users.registration.RegistrationClientArgumentsProvider;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

@Feature("User controller")
@Link("https://github.com/vezhny/vezh-lab/issues/15")
public class UserControllerTests extends ControllerTest {

    @Description("Register user success test")
    @Test
    public void registerUserSuccess() {
        testUtils.logTestStart("Register user success test");

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        anAssert.checkNumberOfUsers(1, users.size());

        anAssert.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success with house part test")
    @Test
    public void registerUserSuccessWithHousePart() {
        testUtils.logTestStart("Register user success with house part test");

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        anAssert.checkNumberOfUsers(1, users.size());

        anAssert.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success without email test")
    @Test
    public void registerUserSuccessWithoutEmail() {
        testUtils.logTestStart("Register user success without email test");

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        anAssert.checkNumberOfUsers(1, users.size());

        anAssert.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
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

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(400, response.getStatus());

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());
        anAssert.checkExceptionMessage(expectedErrorMessage, response);
    }

    @Description("{0}")
    @ParameterizedTest
    @ArgumentsSource(RegisterUserSuccessAgrumentsProvider.class)
    public void registerUserSuccess(String description, String login, String role, String firstName) {
        testUtils.logTestStart(description);

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(200, response.getStatus());

        anAssert.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Description("Login in non-unique test")
    @Test
    public void loginIsNonUnique() {
        testUtils.logTestStart("Login in non-unique test");

        anAssert.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        anAssert.checkResponseCode(200, response.getStatus());

        response = httpPost(Urls.USERS, params);

        anAssert.checkResponseCode(400, response.getStatus());
        anAssert.checkExceptionMessage(String.format(ExceptionMessages.USER_WITH_LOGIN_IS_ALREADY_REGISTERED, login), response);
        anAssert.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Description("{0} tries to register client")
    @ParameterizedTest
    @ArgumentsSource(RegistrationClientArgumentsProvider.class)
    public void clientRegistration(String role) {
        testUtils.logTestStart(role + " tries to register client");

        UserRole userRole = serviceProvider.getDataBaseService().getRoleDao().get(role);
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), userRole);

        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));
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
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        anAssert.checkResponseCode(200, response.getStatus());

        anAssert.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Description("Client tries to register user")
    @Test
    public void clientTriesToRegisterUser() {
        testUtils.logTestStart("Client tries to register user");

        int userId = testUtils.createClient(serviceProvider.getDataBaseService());

        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString()));
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
        params.set(RequestParams.USER_ID, String.valueOf(userId));

        MockHttpServletResponse response = httpPost(Urls.USERS, params);

        anAssert.checkResponseCode(400, response.getStatus());
        anAssert.checkExceptionMessage(ExceptionMessages.THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS, response);
        anAssert.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }
}
