package vezh_bank.assertions;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.EventData;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.mock.web.MockHttpServletResponse;
import vezh_bank.constants.Headers;
import vezh_bank.enums.*;
import vezh_bank.persistence.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class Asserts {

    @Step("Check {1} is not null")
    public void checkNotNull(Object value, String message) {
        Assertions.assertNotNull(value, message);
    }

    @Step("Check {1} is null")
    public void checkNull(Object value, String message) {
        Assertions.assertNull(value, message);
    }

    @Step("Check exception. Expected {0}")
    public void checkException(Class expectedExceptionClass, Executable runnable) {
        Assertions.assertThrows(expectedExceptionClass, runnable);
    }

    @Step("Check {2}. Excepted: {0}. Actual {1}")
    public void checkObject(Object expected, Object actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    @Step("Check {2}. Expected count: {0}")
    public void checkItemsCount(int expectedCount, Collection collection, String message) {
        Assertions.assertEquals(expectedCount, collection.size(), message);
    }

    @Step("Check {2}. Expected: {0}. Actual: {1}")
    public void checkNumber(int expected, int actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    @Step("Check true. {1}")
    public void checkTrue(Boolean value, String message) {
        Assertions.assertTrue(value, message);
    }
}
