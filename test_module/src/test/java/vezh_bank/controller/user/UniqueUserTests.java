package vezh_bank.controller.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
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
import vezh_bank.controller.user.providers.get.UniqueUserLoginArgumentsProvider;
import vezh_bank.extended_tests.ControllerTest;
import vezh_bank.persistence.entity.User;

@Epic("User controller")
@Story("Unique user")
public class UniqueUserTests extends ControllerTest {

    @Description("User is unique")
    @Test
    public void userIsUnique() {
        testUtils.logTestStart("User is unique");

        testUtils.createClient(serviceProvider.getDataBaseService());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, "test");

        MockHttpServletResponse response = httpGet(Urls.USERS + Urls.IS_UNIQUE, params);

        httpAsserts.checkResponseCode(200, response.getStatus());
    }

    @Description("User is not unique")
    @Test
    public void userIsNotUnique() {
        testUtils.logTestStart("User is not unique");

        int userId = testUtils.createClient(serviceProvider.getDataBaseService());
        User user = serviceProvider.getDataBaseService().getUserDao().getById(userId);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, user.getLogin());

        MockHttpServletResponse response = httpGet(Urls.USERS + Urls.IS_UNIQUE, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(ExceptionMessages.userWithLoginAlreadyRegistered(user.getLogin()), response);
    }

    @Description("Login param testing")
    @ArgumentsSource(UniqueUserLoginArgumentsProvider.class)
    @ParameterizedTest
    public void login(String login, String expectedErrorMessage) {
        testUtils.logTestStart("Login parametrized testing");

        testUtils.createClient(serviceProvider.getDataBaseService());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set(RequestParams.LOGIN, login);

        MockHttpServletResponse response = httpGet(Urls.USERS + Urls.IS_UNIQUE, params);

        httpAsserts.checkResponseCode(400, response.getStatus());
        httpAsserts.checkExceptionMessage(expectedErrorMessage, response);
    }
}
