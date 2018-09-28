package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.springframework.mock.web.MockHttpServletResponse;
import vezh_bank.constants.Headers;

public class HttpAsserts extends Asserts {

    @Step("Check response code")
    public void checkResponseCode(int expectedCode, int actualCode) {
        checkObject(expectedCode, actualCode, "Response code");
    }

    @Step("Check response code")
    public void checkResponseCode(int expectedCode, MockHttpServletResponse response) {
        checkResponseCode(expectedCode, response.getStatus());
    }

    @Step("Check current page")
    public void checkCurrentPage(int expected, MockHttpServletResponse response) {
        checkObject(String.valueOf(expected), response.getHeader(Headers.CURRENT_PAGE),
                "Current page");
    }

    @Step("Check pages count")
    public void checkPagesCount(int expected, MockHttpServletResponse response) {
        checkObject(String.valueOf(expected), response.getHeader(Headers.PAGES_COUNT),
                "Pages count");
    }

    @Step("Check exception message")
    public void checkExceptionMessage(String expected, MockHttpServletResponse response) {
        checkObject(expected, response.getHeader(Headers.ERROR_MESSAGE),
                "Exception message");
    }
}
