package vezh_bank.controller;

import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import core.json.Users;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
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
import vezh_bank.controller.providers.users.get.GetUsersArgumentsProvider;
import vezh_bank.controller.providers.users.registration.RegisterUserFailArgumentsProvider;
import vezh_bank.controller.providers.users.registration.RegisterUserSuccessAgrumentsProvider;
import vezh_bank.controller.providers.users.registration.RegistrationClientArgumentsProvider;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Story("User controller")
@Link("https://github.com/vezhny/vezh-lab/issues/15")
public class UserControllerTests extends ControllerTest {

    @Description("Register user success test")
    @Test
    public void registerUserSuccess() {
        testUtils.logTestStart("Register user success test");

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        userAsserts.checkNumberOfUsers(1, users.size());
        userAsserts.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success with house part test")
    @Test
    public void registerUserSuccessWithHousePart() {
        testUtils.logTestStart("Register user success with house part test");

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        userAsserts.checkNumberOfUsers(1, users.size());
        userAsserts.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }

    @Description("Register user success without email test")
    @Test
    public void registerUserSuccessWithoutEmail() {
        testUtils.logTestStart("Register user success without email test");

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(200, response.getStatus());

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        userAsserts.checkNumberOfUsers(1, users.size());
        userAsserts.checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
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

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(400, response.getStatus());

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());
        httpAsserts.checkExceptionMessage(expectedErrorMessage, response);
    }

    @Description("{0}")
    @ParameterizedTest
    @ArgumentsSource(RegisterUserSuccessAgrumentsProvider.class)
    public void registerUserSuccess(String description, String login, String role, String firstName) {
        testUtils.logTestStart(description);

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(200, response.getStatus());
        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Description("Login in non-unique test")
    @Test
    public void loginIsNonUnique() {
        testUtils.logTestStart("Login in non-unique test");

        userAsserts.checkNumberOfUsers(0, serviceProvider.getDataBaseService().getUserDao().selectCount());

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

        httpAsserts.checkResponseCode(200, response.getStatus());

        response = httpPost(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(String.format(ExceptionMessages.USER_WITH_LOGIN_IS_ALREADY_REGISTERED, login), response);
        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
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

        httpAsserts.checkResponseCode(200, response.getStatus());
        userAsserts.checkNumberOfUsers(2, serviceProvider.getDataBaseService().getUserDao().selectCount());
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

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS, response);
        userAsserts.checkNumberOfUsers(1, serviceProvider.getDataBaseService().getUserDao().selectCount());
    }

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18") //TODO: make all links url
    @Description("Get all users test")
    @Test
    public void getAllUsers() throws UnsupportedEncodingException {
        testUtils.logTestStart("Get all users test");

        int userId = testUtils.createNotAClient(serviceProvider.getDataBaseService());

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

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18")
    @Description("Get users when user id absent")
    @Test
    public void getUsersAbsentUserId() {
        testUtils.logTestStart("Get users when user id absent");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.USER_ID_MUST_PRESENT, response);
    }

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18")
    @Description("Get users when user id can't be a number")
    @Test
    public void getUsersUserIdCanNotBeNumber() {
        testUtils.logTestStart("Get users when user id can't be a number");

        String userId = "x";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(String.format(ExceptionMessages.VALUE_CAN_NOT_BE_A_NUMBER, userId), response);
    }

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18")
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

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18")
    @Description("Get users when user is client")
    @Test
    public void getUsersUserIsClient() {
        testUtils.logTestStart("Get users when user is client");

        String userId = String.valueOf(testUtils.createClient(serviceProvider.getDataBaseService()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.USER_ID, userId);

        MockHttpServletResponse response = httpGet(Urls.USERS, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS, response);
    }

    @Link(url = "https://github.com/vezhny/vezh-lab/issues/18")
    @Description("Get users parametrized")
    @ArgumentsSource(GetUsersArgumentsProvider.class)
    @ParameterizedTest
    public void getUsers(int requiredPage, String login, String role, String blocked, String data,
                         int expectedUsersCount, int expectedCurrentPage, int expectedNumberOfPages) throws UnsupportedEncodingException {
        testUtils.logTestStart("Get users parametrized test");
        int userId = testUtils.createNotAClient(serviceProvider.getDataBaseService());

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
