package vezh_bank.controller.user;

import core.json.EventData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vezh_bank.constants.*;
import vezh_bank.controller.user.providers.signIn.InvalidLoginsArgumentsProvider;
import vezh_bank.controller.user.providers.signIn.InvalidPasswordsArgumentsProvider;
import vezh_bank.enums.EventType;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Encryptor;
import vezh_bank.util.TypeConverter;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Epic("User controller")
@Story("User sign in")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/31", url = "https://github.com/vezhny/vezh-lab/issues/31")
public class UserSignInTests extends ControllerTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("User sign in")
    @Description("User sign in success test")
    @Test
    public void userSignInSuccess() throws UnsupportedEncodingException {
        testUtils.logTestStart("User sign in success test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        Encryptor encryptor = new Encryptor();
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());
        params.set(RequestParams.PASSWORD, encryptor.decrypt(user.getPassword()));

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(200, response);
        User userFromResponse = serviceProvider.getDataBaseService().getUserDao()
                .getById(TypeConverter.stringToInt(response.getContentAsString(), -1));
        asserts.checkNotNull(userFromResponse, "User entity");
        asserts.checkObject(user.getLogin(), userFromResponse.getLogin(), "User login");
        asserts.checkNotNull(userFromResponse.getLastSignIn(), "Last sign in date");
        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_SIGN_IN, new EventData(EventDescriptions.userSignedIn(user.getLogin())),
                events.get(0));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User sign in")
    @Description("Invalid login")
    @ArgumentsSource(InvalidLoginsArgumentsProvider.class)
    @ParameterizedTest
    public void invalidLogin(String login) {
        testUtils.logTestStart("Invalid login test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        int attemptsToSignIn = user.getAttemptsToSignIn();
        Encryptor encryptor = new Encryptor();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);
        params.set(RequestParams.PASSWORD, encryptor.decrypt(user.getPassword()));

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        asserts.checkNumber(attemptsToSignIn,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User sign in")
    @Description("Login is null")
    @Test
    public void loginIsNull() {
        testUtils.logTestStart("Login is null test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        int attemptsToSignIn = user.getAttemptsToSignIn();
        Encryptor encryptor = new Encryptor();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.PASSWORD, encryptor.decrypt(user.getPassword()));

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        asserts.checkNumber(attemptsToSignIn,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User sign in")
    @Description("Invalid password first attempt")
    @ArgumentsSource(InvalidPasswordsArgumentsProvider.class)
    @ParameterizedTest
    public void invalidPasswordFirstAttempt(String password) {
        testUtils.logTestStart("Invalid password first attempt test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        int attemptsToSignIn = user.getAttemptsToSignIn();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());
        params.set(RequestParams.PASSWORD, password);

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        asserts.checkNumber(attemptsToSignIn - 1,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User sign in")
    @Description("Password is NULL first attempt")
    @Test
    public void passwordIsNull() {
        testUtils.logTestStart("Password is null first attempt test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        int attemptsToSignIn = user.getAttemptsToSignIn();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        asserts.checkNumber(attemptsToSignIn - 1,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User sign in")
    @Description("Restore attempts to sign in")
    @Test
    public void restoreAttemptsToSignIn() {
        testUtils.logTestStart("Restore attempts to sign in test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        int attemptsToSignIn = user.getAttemptsToSignIn();
        Encryptor encryptor = new Encryptor();
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());
        params.set(RequestParams.PASSWORD, "12345");

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        asserts.checkNumber(attemptsToSignIn - 1,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());

        params.set(RequestParams.PASSWORD, encryptor.decrypt(user.getPassword()));

        response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(200, response);
        asserts.checkNumber(attemptsToSignIn,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_SIGN_IN, new EventData(EventDescriptions.userSignedIn(user.getLogin())),
                events.get(0));
    }

    @Severity(SeverityLevel.MINOR)
    @Feature("User sign in")
    @Description("Blocking user after failed sign in")
    @Test
    public void blockingUserAfterFailedSignIn() {
        testUtils.logTestStart("Blocking user after failed sign in test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());
        params.set(RequestParams.PASSWORD, "12345");

        MockHttpServletResponse response;

        for (int i = UserDefault.ATTEMPTS_TO_SIGN_IN; i > 0; i--) {
            response = httpPost(Urls.USERS + Urls.SIGN_IN, params);
            httpAsserts.checkResponseCode(400, response);
            httpAsserts.checkExceptionMessage(ExceptionMessages.INVALID_LOGIN_OR_PASSWORD, response);
        }

        response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.USER_IS_BLOCKED, response);
        asserts.checkNumber(0,
                serviceProvider.getDataBaseService().getUserDao().getById(user.getId()).getAttemptsToSignIn(),
                "Attempts to sign in");
        List<Event> events = serviceProvider.getDataBaseService().getEventDao().selectAll();
        eventAsserts.checkNumberOfEvents(1, events.size());
        eventAsserts.checkEvent(EventType.USER_BLOCKED, new EventData(EventDescriptions.userHasBeenBlocked(user.getLogin())),
                events.get(0));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("User sign in")
    @Description("User is blocked")
    @Test
    public void userIsBlocked() {
        testUtils.logTestStart("User is blocked test");

        UserRole role = serviceProvider.getDataBaseService().getRoleDao().get(Role.CLIENT.toString());
        int userId = testUtils.createUser(serviceProvider.getDataBaseService(), role);
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);
        user.setBlocked(true);
        serviceProvider.getDataBaseService().getUserDao().update(user);
        Encryptor encryptor = new Encryptor();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());
        params.set(RequestParams.PASSWORD, encryptor.decrypt(user.getPassword()));

        MockHttpServletResponse response = httpPost(Urls.USERS + Urls.SIGN_IN, params);

        httpAsserts.checkResponseCode(400, response);
        httpAsserts.checkExceptionMessage(ExceptionMessages.USER_IS_BLOCKED, response);
        eventAsserts.checkNumberOfEvents(0, serviceProvider.getDataBaseService().getEventDao().selectCount());
    }
}
