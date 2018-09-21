package vezh_bank.extended_tests;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.EventData;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Event;
import vezh_bank.persistence.entity.User;

public class ServiceTest extends RootTest {

    @Step("Check event. Expected type: {0}. Expected data: {1}. Actual event: {2}")
    protected void checkEvent(EventType expectedEventType, EventData expectedEventData, Event actualEvent) {
        EventDTO eventDTO = new EventDTO(actualEvent);

        Assertions.assertNotNull(eventDTO.getDate(), "Date");
        Assertions.assertEquals(expectedEventType, eventDTO.getType());
        Assertions.assertEquals(expectedEventData.toString(), eventDTO.getEventData().toString());
    }

    @Step("Check user. Expected login: {0}. Expected password: {1}. Expected role: {2}. Expected config: {3}. " +
            "Expected attempts to sign in: {4}. Expected blocked status: {5}. Expected data: {7}. Actual user: {8}")
    protected void checkUser(String expectedLogin, String expectedPassword, UserRoleDTO expectedRole,
                             UserConfig expectedUserConfig, int expectedAttemptsToSignIn,
                             boolean expectedBlocked, String expectedLastSignIn,
                             UserData expectedUserData, User actualUser) {
        UserDTO userDTO = new UserDTO(actualUser);

        Assertions.assertEquals(expectedLogin, userDTO.getLogin(), "Login");
        Assertions.assertEquals(expectedPassword, userDTO.getPassword(), "Password");
        Assertions.assertEquals(expectedRole.getName(), userDTO.getRole().getName(), "Role");
        Assertions.assertEquals(expectedUserConfig.toString(), userDTO.getConfig().toString(), "Config");
        Assertions.assertEquals(expectedAttemptsToSignIn, userDTO.getAttemptsToSignIn(), "Attempts to sign in");
        Assertions.assertEquals(expectedBlocked, userDTO.isBlocked(), "Blocked");
        Assertions.assertEquals(expectedLastSignIn, userDTO.getLastSignIn(), "Last sign in");
        Assertions.assertEquals(expectedUserData.toString(), userDTO.getData().toString(), "Data");
    }
}
